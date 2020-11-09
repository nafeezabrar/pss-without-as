package pss.variable.generation;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.valuemap.SingleValueMap;
import pss.data.valuemap.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SequentialSingleOoiValueGenerator implements OoiValueGenerable<SingleValueMap> {
    @Override
    public SingleValueMap generateValueMap(Map<Dimension, Set<Ooi>> ooiMap) {
        Map<SingleOoiCombination, Value> ooiValues = new HashMap<>();
        Dimension dimension = ooiMap.keySet().iterator().next();
        Set<Ooi> oois = ooiMap.get(dimension);
        for (Ooi ooi : oois) {
            SingleOoiCombination ooiCombination = new SingleOoiCombination(ooi);
            ooiValues.put(ooiCombination, new Value(ooi.getId()));
        }
        return new SingleValueMap(ooiValues);
    }
}
