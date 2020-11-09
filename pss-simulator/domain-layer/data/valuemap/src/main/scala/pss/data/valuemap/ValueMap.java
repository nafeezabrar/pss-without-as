package pss.data.valuemap;

import pss.data.ooi.combination.OoiCombination;

import java.util.Map;

public abstract class ValueMap<TOoiCombination extends OoiCombination> {
    protected final Map<TOoiCombination, Value> valueMap;

    protected ValueMap(Map<TOoiCombination, Value> valueMap) {
        this.valueMap = valueMap;
    }

    public Value getValue(TOoiCombination ooiCombination) {
        return valueMap.get(ooiCombination);
    }

    public Map<TOoiCombination, Value> getValues() {
        return valueMap;
    }
}
