package pss.data.valuemap;

import pss.data.ooi.combination.SingleOoiCombination;

import java.util.Map;

public class SingleValueMap extends ValueMap<SingleOoiCombination> {
    public SingleValueMap(Map<SingleOoiCombination, Value> singleOoiCombinationValueMap) {
        super(singleOoiCombinationValueMap);
    }
}
