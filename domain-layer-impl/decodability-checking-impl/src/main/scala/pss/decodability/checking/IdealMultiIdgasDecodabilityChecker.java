package pss.decodability.checking;

import pss.data.dimension.Dimension;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.valuemap.Value;

import java.util.HashMap;
import java.util.Map;

public class IdealMultiIdgasDecodabilityChecker extends MultiIdgasDecodabilityChecker {

    public IdealMultiIdgasDecodabilityChecker(MultiOcTable ocTable) {
        super(ocTable);
    }

    @Override
    public boolean isDecoded(Value value) {
        MultiOcRow ocRow = ocTable.getOcRowMap().get(value);
        return getDecodedLocalOoiCombination(ocRow) != null;
    }

    @Override
    public MultiLocalOoiCombination getDecodedLocalOoiCombination(Value value) {
        MultiOcRow ocRow = ocTable.getOcRowMap().get(value);
        return getDecodedLocalOoiCombination(ocRow);
    }

    @Override
    public Map<MultiLocalOoiCombination, Value> getDecodedValueMap() {
        Map<MultiLocalOoiCombination, Value> decodedValueMap = new HashMap<>();
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        for (Map.Entry<Value, MultiOcRow> entry : ocRowMap.entrySet()) {
            MultiLocalOoiCombination decodedLocalOoiCombination = getDecodedLocalOoiCombination(entry.getValue());
            if (decodedLocalOoiCombination != null) {
                decodedValueMap.put(decodedLocalOoiCombination, entry.getKey());
            }

        }
        return decodedValueMap;
    }

    protected MultiLocalOoiCombination getDecodedLocalOoiCombination(MultiOcRow ocRow) {
        if (ocRow == null)
            return null;
        int decodedOoiId;
        boolean isDecoded = true;
        int maxCount;
        int totalCount = ocRow.getTotalReportCount();
        Map<Dimension, Integer> decodedIdMap = new HashMap<>();
        for (Dimension dimension : ocTable.getDimensions()) {
            decodedOoiId = -1;
            maxCount = 0;
            for (OcCell ocCell : ocRow.getOcCellMap().get(dimension).values()) {
                if (ocCell.getOcCount() == totalCount) {
                    if (totalCount == maxCount) {
                        isDecoded = false;
                        break;
                    }
                    maxCount = totalCount;
                    decodedOoiId = ocCell.getOoiId();
                }
            }
            if (!isDecoded) {
                break;
            }
            decodedIdMap.put(dimension, decodedOoiId);
        }
        if (isDecoded) {
            return new MultiLocalOoiCombination(decodedIdMap);
        }
        return null;
    }
}
