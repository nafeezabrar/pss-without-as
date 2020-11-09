package pss.anonymization.dgas;

import org.junit.Before;
import org.junit.Test;
import pss.data.anonymity.SingleAnonymity;
import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.DecodedState;
import pss.data.combination_table.ValueKey;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.library.combinatorics.CombinatoricsUtility;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class SingleDecodedStateTableOptimalUpdaterTest {
    private int A = 1, B = 2, C = 3, D = 4;
    private Set<ValueKey> valueKeySet;
    private int vA = 10, vB = 20, vC = 30, vD = 40;
    private int localOois[] = new int[]{A, B, C, D};
    private DecodedMapTable<SingleLocalOoiCombination> decodedMapTable;
    private SingleDecodedMapTableOptimalUpdater tableUpdater;
    private SingleLocalOoiCollection localOoiCollection;

    @Before
    public void setUp() throws Exception {
        initLocalOoiCollection();
        initValueKeys();
        Set<DecodedState<SingleLocalOoiCombination>> decodedStates = makeAllPossibleDecodedMap(localOois);
        decodedMapTable = new DecodedMapTable<>(decodedStates);
        tableUpdater = new SingleDecodedMapTableOptimalUpdater(decodedMapTable, localOoiCollection, valueKeySet);
    }

    private void initValueKeys() {
        valueKeySet = new HashSet<>();
        int totalOois = localOois.length;
        for (int i = 0; i < totalOois; i++) {
            valueKeySet.add(new ValueKey(i));
        }
    }

    private void initLocalOoiCollection() {
        Set<Integer> ooiIds = new HashSet<>();
        for (int i = 0; i < localOois.length; i++) {
            ooiIds.add(localOois[i]);
        }
        localOoiCollection = new SingleLocalOoiCollection(ooiIds);
    }

    @Test
    public void testTableUpdaterChooseAnonymizationInEachStepCorrectly() throws Exception {
        SingleAnonymity anonymity = new SingleAnonymity(3);
        int anonymityValue = anonymity.getAnonymity();
        checkAnonymizationWith(A, vA, anonymityValue, 18);
        anonymize(C, vC, anonymityValue);
        anonymize(B, vB, anonymityValue);
        anonymize(D, vD, anonymityValue);
        anonymize(A, vA, anonymityValue);
        anonymize(B, vB, anonymityValue);
        anonymize(C, vC, anonymityValue);
        anonymize(D, vD, anonymityValue);
        anonymize(B, vB, anonymityValue);
        Set<DecodedState<SingleLocalOoiCombination>> remainingDecodedStates = tableUpdater.decodedMapTable.getDecodedStates();
        assertEquals(1, remainingDecodedStates.size());
    }

    private void checkAnonymizationWith(int reportedOoi, int reportedValue, int anonymity, int expectedCountOfRemainingDecodedState) {
        SingleLocalOoiCollection anonymizedCollection = anonymize(reportedOoi, reportedValue, anonymity);
        assertEquals(anonymizedCollection.getLocalOoiSet().size(), anonymity);
        int countOfRemainingDecodedStates = tableUpdater.decodedMapTable.getDecodedStates().size();
        assertEquals(expectedCountOfRemainingDecodedState, countOfRemainingDecodedStates);
    }

    private SingleLocalOoiCollection anonymize(int reportedOoi, int reporetedValue, int anonymity) {
        SingleLocalOoiCombination reportedOoiCombination = new SingleLocalOoiCombination(reportedOoi);

        Set<SingleLocalOoiCollection> possibleAnonymizationSet = makePossibleAnonymizations(reportedOoi, new SingleAnonymity(anonymity));
        return tableUpdater.chooseBestAnonymizedCollectionAndUpdate(reportedOoiCombination, new Value(reporetedValue), possibleAnonymizationSet);
    }

    private Set<SingleLocalOoiCollection> makePossibleAnonymizations(int reportedOoi, SingleAnonymity singleAnonymity) {
        Set<Integer> localOoisForAnonymization = new HashSet<>();
        for (int i = 0; i < localOois.length; i++) {
            if (localOois[i] != reportedOoi)
                localOoisForAnonymization.add(localOois[i]);
        }

        Set<Set<Integer>> possibleAnonymizations = CombinatoricsUtility.getCombinations(localOoisForAnonymization, singleAnonymity.getAnonymity() - 1);
        Set<SingleLocalOoiCollection> anonymizationSet = new HashSet<>();
        for (Set<Integer> anonymization : possibleAnonymizations) {
            anonymization.add(reportedOoi);
            anonymizationSet.add(new SingleLocalOoiCollection(anonymization));
        }
        return anonymizationSet;
    }

    private Set<DecodedState<SingleLocalOoiCombination>> makeAllPossibleDecodedMap(int[] localOois) {
        Set<DecodedState<SingleLocalOoiCombination>> decodedStates = new HashSet<>();
        Set<List<Integer>> permutations = CombinatoricsUtility.getPermutations(localOois);

        for (List<Integer> permutation : permutations) {
            Map<ValueKey, SingleLocalOoiCombination> map = new HashMap<>();
            Iterator<ValueKey> valueKeyIterator = valueKeySet.iterator();
            for (int i = 0; i < localOois.length; i++) {
                SingleLocalOoiCombination ooiCombination = new SingleLocalOoiCombination(permutation.get(i));
                map.put(valueKeyIterator.next(), ooiCombination);
            }
            decodedStates.add(new DecodedState<>(map));
        }
        return decodedStates;
    }
}