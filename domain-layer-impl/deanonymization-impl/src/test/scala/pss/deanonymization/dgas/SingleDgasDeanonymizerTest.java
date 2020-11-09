package pss.deanonymization.dgas;

import org.junit.Before;
import org.junit.Test;
import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.DecodedState;
import pss.data.combination_table.ValueKey;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.deanonymization.dgas.decodingMapTable.updating.SingleDgasDecodingTableUpdater;
import pss.library.combinatorics.CombinatoricsUtility;
import pss.report.finalreport.SingleFinalReport;
import pss.report.finalreport.SingleFinalReportData;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class SingleDgasDeanonymizerTest {
    private SingleDgasDeanonymizer deanonymizer;
    private SingleLocalOoiCollection ooiCollection;
    private int ooiOne, ooiTwo, ooiThree, ooiFour;
    private int[] oois;
    private int v1 = 10, v2 = 20, v3 = 30, v4 = 40;
    private Set<ValueKey> valueKeySet;
    private SingleFinalReport[] finalReports;

    @Before
    public void setUp() throws Exception {
        initOois();
        initOoiCollection();
        initValueKeys();
        initFinalReports();
    }

    private void initOoiCollection() {
        Set<Integer> ooiSet = new HashSet<>();
        for (int i = 0; i < oois.length; i++) {
            ooiSet.add(oois[i]);
        }
        ooiCollection = new SingleLocalOoiCollection(ooiSet);
    }

    private void initValueKeys() {
        valueKeySet = new HashSet<>();
        int totalOois = oois.length;
        for (int i = 0; i < totalOois; i++) {
            valueKeySet.add(new ValueKey(i));
        }
    }

    private void initOois() {
        oois = new int[4];
        for (int i = 0; i < 4; i++) {
            oois[i] = i + 1;
        }
        ooiOne = 1;
        ooiTwo = 2;
        ooiThree = 3;
        ooiFour = 4;
    }

    private void initFinalReports() {
        finalReports = new SingleFinalReport[7];
        finalReports[0] = makeFinalReport(1, new int[]{ooiOne, ooiTwo, ooiThree}, v1); // 1, 2, 3 = 10
        finalReports[1] = makeFinalReport(2, new int[]{ooiOne, ooiTwo, ooiThree}, v3); // 1, 2, 3 = 30
        finalReports[2] = makeFinalReport(3, new int[]{ooiOne, ooiTwo, ooiThree}, v2); // 1, 2, 3 = 20
        finalReports[3] = makeFinalReport(4, new int[]{ooiOne, ooiTwo, ooiFour}, v4); // 1, 2, 4 = 40
        finalReports[4] = makeFinalReport(5, new int[]{ooiOne, ooiTwo, ooiFour}, v2); // 1, 2, 4 = 20
        finalReports[5] = makeFinalReport(6, new int[]{ooiTwo, ooiThree, ooiFour}, v2); // 2, 3, 4 = 20
        finalReports[6] = makeFinalReport(7, new int[]{ooiTwo, ooiThree, ooiFour}, v3); // 2, 3, 4 = 30
    }

    private SingleFinalReport makeFinalReport(int reportId, int[] oois, int value) {
        Set<Integer> ooiIdSet = new HashSet<>();
        for (int i = 0; i < oois.length; i++) {
            ooiIdSet.add(oois[i]);
        }
        SingleFinalReportData reportData = new SingleFinalReportData(reportId, new SingleLocalOoiCollection(ooiIdSet));
        return new SingleFinalReport(reportId, new Value(value), reportData);

    }

    @Test
    public void testDecoderCanDecodeCorrectly() throws Exception {
        Set<DecodedState<SingleLocalOoiCombination>> decodedStates = makeAllPossibleDecodedMap(oois);
        DecodedMapTable<SingleLocalOoiCombination> decodedMapTable = new DecodedMapTable<>(decodedStates);
        DgasDecodingTableUpdatable<SingleFinalReport> decodingTableUpdater = new SingleDgasDecodingTableUpdater(decodedMapTable, ooiCollection);
        deanonymizer = new SingleDgasDeanonymizer(decodedMapTable, decodingTableUpdater, valueKeySet);
        for (int i = 0; i < finalReports.length; i++) {
            deanonymizer.deanonymize(finalReports[i]);
        }
        assertEquals(1, decodedMapTable.getTotalCombinations());

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