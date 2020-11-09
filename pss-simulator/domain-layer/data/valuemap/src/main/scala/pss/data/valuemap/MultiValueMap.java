package pss.data.valuemap;

import pss.data.ooi.combination.MultiOoiCombination;

import java.util.Map;

public class MultiValueMap extends ValueMap<MultiOoiCombination> {
    public MultiValueMap(Map<MultiOoiCombination, Value> multiOoiCombinationValueMap) {
        super(multiOoiCombinationValueMap);
    }
}
