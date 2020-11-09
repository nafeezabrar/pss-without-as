package pss.deanonymization.dgas;

import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.DecodedState;
import pss.data.combination_table.ValueKey;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.deanonymization.Deanonymizable;
import pss.report.finalreport.SingleFinalReport;
import pss.result.deanonymization.SingleDeanonymizationResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SingleDgasDeanonymizer extends DgasDeanonymizer<SingleLocalOoiCombination, SingleFinalReport> implements Deanonymizable<SingleFinalReport, SingleDeanonymizationResult, SingleLocalOoiCombination> {


    public SingleDgasDeanonymizer(DecodedMapTable<SingleLocalOoiCombination> decodedMapTable, DgasDecodingTableUpdatable<SingleFinalReport> decodingTableUpdater, Set<ValueKey> valueKeys) {
        super(decodedMapTable, decodingTableUpdater, valueKeys);
    }

    @Override
    public SingleDeanonymizationResult deanonymize(SingleFinalReport finalReport) {
        Value reportedValue = finalReport.getValue();
        assignValue(reportedValue);
        ValueKey reportedValueKey = valueToValueKeyMap.get(reportedValue);
        decodingTableUpdater.update(finalReport, reportedValueKey);
        Map<SingleLocalOoiCombination, Value> decodedValueMap = getDecodedValueMap();
        return new SingleDeanonymizationResult(finalReport, null, decodedValueMap.size(), false);
    }

    private void assignValue(Value reportedValue) {
        if (!assignedValues.contains(reportedValue)) {
            ValueKey unassignedKey = getUnassignedValueKey();
            valueToValueKeyMap.put(reportedValue, unassignedKey);
            assignedValues.add(reportedValue);
        }
    }

    private ValueKey getUnassignedValueKey() {
        Collection<ValueKey> assignedValueKeys = valueToValueKeyMap.values();
        for (ValueKey valueKey : this.valueKeys) {
            if (!assignedValueKeys.contains(valueKey))
                return valueKey;
        }
        return null;
    }

    @Override
    public Map<SingleLocalOoiCombination, Value> getDecodedValueMap() {
        if (decodedMapTable.getTotalCombinations() == 1) {
            return getDecodedValueMapFromTable();
        }
        return new HashMap<>();
    }

    private Map<SingleLocalOoiCombination, Value> getDecodedValueMapFromTable() {
        DecodedState<SingleLocalOoiCombination> finalDecodedState = decodedMapTable.getDecodedStates().iterator().next();
        Map<ValueKey, SingleLocalOoiCombination> valueToOoiMap = finalDecodedState.getValueToOoiMap();
        Map<SingleLocalOoiCombination, Value> decodedValueMap = new HashMap<>();
        for (Value value : valueToValueKeyMap.keySet()) {
            ValueKey valueKey = valueToValueKeyMap.get(value);
            SingleLocalOoiCombination ooiCombination = valueToOoiMap.get(valueKey);
            decodedValueMap.put(ooiCombination, value);
        }
        return decodedValueMap;
    }

}
