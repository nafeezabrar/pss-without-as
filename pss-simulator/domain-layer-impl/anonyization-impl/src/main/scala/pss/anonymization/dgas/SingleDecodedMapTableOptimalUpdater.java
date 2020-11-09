package pss.anonymization.dgas;

import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.DecodedState;
import pss.data.combination_table.NotAllowedMappings;
import pss.data.combination_table.ValueKey;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;

import java.util.*;

public class SingleDecodedMapTableOptimalUpdater extends DecodedMapTableOptimalUpdater<SingleLocalOoiCombination, SingleLocalOoiCollection> {
    protected final Set<ValueKey> valueKeys;
    private Map<Value, ValueKey> valueToValueKeyMap;
    private Set<Value> assignedValues;
    private Map<Value, SingleLocalOoiCombination> allDecodingConstraint;

    public SingleDecodedMapTableOptimalUpdater(DecodedMapTable<SingleLocalOoiCombination> decodedMapTable, SingleLocalOoiCollection localOoiCollection, Set<ValueKey> valueKeys) {
        super(decodedMapTable, localOoiCollection);
        this.valueKeys = valueKeys;
        valueToValueKeyMap = new HashMap<>();
        this.assignedValues = new HashSet<>();
        this.allDecodingConstraint = new HashMap<>();
    }


    @Override
    public SingleLocalOoiCollection chooseBestAnonymizedCollectionAndUpdate(SingleLocalOoiCombination reportedOoiCombination, Value reportedValue, Set<SingleLocalOoiCollection> possibleAnonymizedCollections) {
        assignValue(reportedValue);
        ValueKey valueKey = valueToValueKeyMap.get(reportedValue);
        SingleLocalOoiCollection bestAnonymization = null;
        Set<DecodedState> decodedStateToRemove = new HashSet<>();
        int maxNumberOfDecodedStateRemoved = -1;
        for (SingleLocalOoiCollection anonymization : possibleAnonymizedCollections) {
            Set<DecodedState> removableDecodingStates = getRemovableDecodingStates(anonymization, valueKey);
            int countOfRemovableDecodingStates = removableDecodingStates.size();
            if (maxNumberOfDecodedStateRemoved < countOfRemovableDecodingStates) {
                maxNumberOfDecodedStateRemoved = countOfRemovableDecodingStates;
                bestAnonymization = anonymization;
                decodedStateToRemove.clear();
                decodedStateToRemove.addAll(removableDecodingStates);
            }
        }
        decodedMapTable.removeDecodedStates(decodedStateToRemove);
        return bestAnonymization;
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

    private Set<DecodedState> getRemovableDecodingStates(SingleLocalOoiCollection anonymization, ValueKey valueKey) {
        NotAllowedMappings notAllowedMappings = getOppositeConditionFromAnonymization(anonymization, valueKey);
        Set<DecodedState> alignedDecodedStates = decodedMapTable.getAlignedDecodedStates(notAllowedMappings);
        return alignedDecodedStates;
    }

    private NotAllowedMappings getOppositeConditionFromAnonymization(SingleLocalOoiCollection anonymization, ValueKey valueKey) {
        Map<ValueKey, SingleLocalOoiCombination> constraintMap = new HashMap<>();
        Set<Integer> excludedLocalOois = getExcludedOoiSet(anonymization);
        for (Integer excludedLocalOoi : excludedLocalOois) {
            constraintMap.put(valueKey, new SingleLocalOoiCombination(excludedLocalOoi));
        }
        return new NotAllowedMappings(constraintMap);
    }

    private Set<Integer> getExcludedOoiSet(SingleLocalOoiCollection anonymization) {
        Set<Integer> excludedLocalOois = new HashSet<>();
        Set<Integer> anonymizedLocalOoiSet = anonymization.getLocalOoiSet();
        for (Integer localOoi : localOoiCollection.getLocalOoiSet()) {
            if (!anonymizedLocalOoiSet.contains(localOoi)) {
                excludedLocalOois.add(localOoi);
            }
        }
        return excludedLocalOois;
    }
}
