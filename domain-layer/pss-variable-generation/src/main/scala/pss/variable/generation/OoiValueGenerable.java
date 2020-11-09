package pss.variable.generation;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.valuemap.ValueMap;

import java.util.Map;
import java.util.Set;

public interface OoiValueGenerable<TValueMap extends ValueMap> {
    TValueMap generateValueMap(Map<Dimension, Set<Ooi>> ooiMap);
}
