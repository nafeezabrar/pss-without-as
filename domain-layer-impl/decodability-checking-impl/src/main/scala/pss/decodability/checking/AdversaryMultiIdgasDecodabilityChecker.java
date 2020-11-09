package pss.decodability.checking;

import pss.data.dimension.Dimension;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.valuemap.Value;

import java.util.HashMap;
import java.util.Map;

public class AdversaryMultiIdgasDecodabilityChecker extends IdealMultiIdgasDecodabilityChecker {
    public AdversaryMultiIdgasDecodabilityChecker(MultiOcTable ocTable) {
        super(ocTable);
    }

    @Override
    public Map<MultiLocalOoiCombination, Value> getDecodedValueMap() {
        Map<MultiLocalOoiCombination, Value> decodedValueMap = new HashMap<>();
        Map<MultiLocalOoiCombination, Integer> decodedByOcCounts = new HashMap<>();
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();

        for (Map.Entry<Value, MultiOcRow> entry : ocRowMap.entrySet()) {
            MultiOcRow ocRow = entry.getValue();
            MultiLocalOoiCombination decodedLocalOoiCombination = getDecodedLocalOoiCombination(ocRow);
            if (decodedLocalOoiCombination != null) {
                Value value = entry.getKey();
                if (decodedValueMap.containsKey(decodedLocalOoiCombination)) {
                    boolean isBetterOoiFound = isBetterOoiFound(decodedByOcCounts, ocRow, decodedLocalOoiCombination);
                    if (!isBetterOoiFound)
                        break;
                }
                addToDecodedValueMap(decodedValueMap, decodedByOcCounts, ocRow, decodedLocalOoiCombination, value);

            }
        }
        return decodedValueMap;
    }

    private boolean isBetterOoiFound(Map<MultiLocalOoiCombination, Integer> decodedByOcCounts, MultiOcRow ocRow, MultiLocalOoiCombination decodedLocalOoiCombination) {
        int previousOcCount = decodedByOcCounts.get(decodedLocalOoiCombination);
        int currentOcCount = getOcCount(ocRow, decodedLocalOoiCombination);
        return currentOcCount > previousOcCount;
    }

    private void addToDecodedValueMap(Map<MultiLocalOoiCombination, Value> decodedValueMap, Map<MultiLocalOoiCombination, Integer> decodedByOcCounts, MultiOcRow ocRow, MultiLocalOoiCombination decodedLocalOoiCombination, Value value) {
        decodedValueMap.put(decodedLocalOoiCombination, value);
        int ocCount = getOcCount(ocRow, decodedLocalOoiCombination);
        decodedByOcCounts.put(decodedLocalOoiCombination, ocCount);
    }

    private int getOcCount(MultiOcRow ocRow, MultiLocalOoiCombination localOoiCombination) {
        Map<Dimension, Map<Integer, OcCell>> ocCellMap = ocRow.getOcCellMap();
        Map<Dimension, Integer> ooiIdMap = localOoiCombination.getLocalOoiMap();
        int totalOcCount = 0;
        for (Dimension dimension : ocCellMap.keySet()) {
            Map<Integer, OcCell> integerOcCellMap = ocCellMap.get(dimension);
            Integer localOoiId = ooiIdMap.get(dimension);
            OcCell ocCell = integerOcCellMap.get(localOoiId);
            totalOcCount += ocCell.getOcCount();
        }
        return totalOcCount;
    }
}
