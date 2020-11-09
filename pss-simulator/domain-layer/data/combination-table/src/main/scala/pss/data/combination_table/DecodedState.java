package pss.data.combination_table;


import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;

import java.util.Map;
import java.util.Objects;

public class DecodedState<TLocalOoiCombination extends LocalOoiCombination> {
    protected Map<ValueKey, TLocalOoiCombination> valueToOoiMap;

    public DecodedState(Map<ValueKey, TLocalOoiCombination> ValueToOoiMap) {
        this.valueToOoiMap = ValueToOoiMap;
    }

    public Map<ValueKey, TLocalOoiCombination> getValueToOoiMap() {
        return valueToOoiMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecodedState<?> that = (DecodedState<?>) o;
        return Objects.equals(valueToOoiMap, that.valueToOoiMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueToOoiMap);
    }

    public boolean isSatisfied(NotAllowedMappings<TLocalOoiCombination> notAllowedMappings) {

        Map<ValueKey, TLocalOoiCombination> constraints = notAllowedMappings.getConstraints();
        for (ValueKey valueKey : valueToOoiMap.keySet()) {
            SingleLocalOoiCombination ooi = (SingleLocalOoiCombination) valueToOoiMap.get(valueKey);
            for (ValueKey constraintValueKey : constraints.keySet()) {
                TLocalOoiCombination constraintOoi = constraints.get(constraintValueKey);
                if (ooi.equals(constraintOoi) && !valueKey.equals(constraintValueKey))
                    return false;
                }


        }
        return true;
    }
}
