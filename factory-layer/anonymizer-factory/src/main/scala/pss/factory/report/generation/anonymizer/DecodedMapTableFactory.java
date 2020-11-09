package pss.factory.report.generation.anonymizer;

import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.DecodedState;
import pss.data.combination_table.ValueKey;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.library.combinatorics.CombinatoricsUtility;

import java.util.*;

public class DecodedMapTableFactory {
    public static DecodedMapTable<SingleLocalOoiCombination> createSingleDecodedMapTable(SingleLocalOoiCollection localOoiCollection, Set<ValueKey> valueKeySet) {
        Set<Integer> localOoiSet = localOoiCollection.getLocalOoiSet();
        int[] ooiArray = new int[localOoiSet.size()];
        int i = 0;
        for (Integer ooiId : localOoiSet) {
            ooiArray[i] = ooiId;
            i++;
        }

        Set<DecodedState<SingleLocalOoiCombination>> decodedStates = makeAllPossibleDecodedMap(ooiArray, valueKeySet);
        return new DecodedMapTable<SingleLocalOoiCombination>(decodedStates);
    }

    private static Set<DecodedState<SingleLocalOoiCombination>> makeAllPossibleDecodedMap(int[] localOois, Set<ValueKey> valueKeySet) {
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
