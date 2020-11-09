package pss.decodability.checking;

import pss.data.oc_table.OcCell;
import pss.data.oc_table.SingleOcRow;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;

import java.util.HashMap;
import java.util.Map;

public class AdversarySingleIdgasDecodabilityChecker extends IdealSingleIdgasDecodabilityChecker {
    public AdversarySingleIdgasDecodabilityChecker(SingleOcTable ocTable) {
        super(ocTable);
    }

    @Override
    public Map<SingleLocalOoiCombination, Value> getDecodedValueMap() {
        Map<SingleLocalOoiCombination, Value> decodedValueMap = new HashMap<>();
        Map<SingleLocalOoiCombination, Integer> decodedByOcCounts = new HashMap<>();
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();

        for (Map.Entry<Value, SingleOcRow> entry : ocRowMap.entrySet()) {
            SingleOcRow ocRow = entry.getValue();
            SingleLocalOoiCombination decodedLocalOoiCombination = getDecodedLocalOoiCombination(ocRow);
            if (decodedLocalOoiCombination != null) {
                Value value = entry.getKey();
                if (decodedValueMap.containsKey(decodedLocalOoiCombination)) {
                    boolean isBetterOoiFound = isBetterOoiFound(decodedByOcCounts, ocRow, decodedLocalOoiCombination);
                    if (!isBetterOoiFound)
                        continue;
                }
                addToDecodedValueMap(decodedValueMap, decodedByOcCounts, ocRow, decodedLocalOoiCombination, value);

            }
        }
        return decodedValueMap;
    }

    private boolean isBetterOoiFound(Map<SingleLocalOoiCombination, Integer> decodedByOcCounts, SingleOcRow ocRow, SingleLocalOoiCombination decodedLocalOoiCombination) {
        int previousOcCount = decodedByOcCounts.get(decodedLocalOoiCombination);
        int currentOcCount = getOcCount(ocRow, decodedLocalOoiCombination);
        return currentOcCount > previousOcCount;
    }

    private void addToDecodedValueMap(Map<SingleLocalOoiCombination, Value> decodedValueMap, Map<SingleLocalOoiCombination, Integer> decodedByOcCounts, SingleOcRow ocRow, SingleLocalOoiCombination decodedLocalOoiCombination, Value value) {
        decodedValueMap.put(decodedLocalOoiCombination, value);
        int ocCount = getOcCount(ocRow, decodedLocalOoiCombination);
        decodedByOcCounts.put(decodedLocalOoiCombination, ocCount);
    }

    private int getOcCount(SingleOcRow ocRow, SingleLocalOoiCombination localOoiCombination) {
        Map<Integer, OcCell> ocCellMap = ocRow.getOcCellMap();
        int localOoiId = localOoiCombination.getLocalOoi();
        OcCell ocCell = ocCellMap.get(localOoiId);
        return ocCell.getOcCount();
    }
}
