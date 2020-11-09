package pss.decodability.checking;

import pss.data.oc_table.OcCell;
import pss.data.oc_table.OcRow;
import pss.data.oc_table.SingleOcRow;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IdealSingleIdgasDecodabilityChecker extends SingleIdgasDecodabilityChecker {

    private Set<Integer> decodedOoiIdSet;
    private Map<OcRow, SingleLocalOoiCombination> decodedOoiMap;

    public IdealSingleIdgasDecodabilityChecker(SingleOcTable ocTable) {
        super(ocTable);
        this.decodedOoiIdSet = new HashSet<>();
        this.decodedOoiMap = new HashMap<>();
    }

    @Override
    public boolean isDecoded(Value value) {
        SingleOcRow ocRow = ocTable.getOcRowMap().get(value);
        return getDecodedLocalOoiCombination(ocRow) != null;
    }

    @Override
    public SingleLocalOoiCombination getDecodedLocalOoiCombination(Value value) {
        SingleOcRow ocRow = ocTable.getOcRowMap().get(value);
        return getDecodedLocalOoiCombination(ocRow);
    }

    @Override
    public Map<SingleLocalOoiCombination, Value> getDecodedValueMap() {
        Map<SingleLocalOoiCombination, Value> decodedValueMap = new HashMap<>();
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();

        for (Map.Entry<Value, SingleOcRow> entry : ocRowMap.entrySet()) {
            SingleLocalOoiCombination decodedLocalOoiCombination = getDecodedLocalOoiCombination(entry.getValue());
            if (decodedLocalOoiCombination != null) {
                decodedValueMap.put(decodedLocalOoiCombination, entry.getKey());
            }

        }
        return decodedValueMap;
    }

    protected SingleLocalOoiCombination getDecodedLocalOoiCombination(SingleOcRow ocRow) {
        if (ocRow == null)
            return null;
        if (decodedOoiMap.containsKey(ocRow))
            return decodedOoiMap.get(ocRow);
        int decodedOoiId = -1;
        boolean isDecoded = false;
        int totalCount = ocRow.getTotalReportCount();

        for (OcCell ocCell : ocRow.getOcCellMap().values()) {
            if (decodedOoiIdSet.contains(ocCell.getOoiId()))
                continue;
            if (ocCell.getOcCount() == totalCount) {
                if (isDecoded) {
                    isDecoded = false;
                    break;
                } else {
                    isDecoded = true;
                    decodedOoiId = ocCell.getOoiId();
                }
            }
        }
        if (isDecoded) {
            decodedOoiIdSet.add(decodedOoiId);
            SingleLocalOoiCombination decodedOoiCombination = new SingleLocalOoiCombination(decodedOoiId);
            decodedOoiMap.put(ocRow, decodedOoiCombination);
            return decodedOoiCombination;
        }
        return null;
    }
}
