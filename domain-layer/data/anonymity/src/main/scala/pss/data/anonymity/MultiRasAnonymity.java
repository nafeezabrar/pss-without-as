package pss.data.anonymity;

import pss.data.dimension.Dimension;

import java.util.Map;

public class MultiRasAnonymity extends MultiAnonymity {
    protected final Map<Dimension, Integer> replacingAnonymityMap;

    public MultiRasAnonymity(Map<Dimension, Integer> anonymities, Map<Dimension, Integer> replacingAnonymityMap) {
        super(anonymities);
        this.replacingAnonymityMap = replacingAnonymityMap;
    }

    public Map<Dimension, Integer> getReplacingAnonymityMap() {
        return replacingAnonymityMap;
    }
}
