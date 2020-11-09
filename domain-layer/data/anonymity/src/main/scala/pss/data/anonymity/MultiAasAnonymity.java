package pss.data.anonymity;

import pss.data.dimension.Dimension;

import java.util.Map;

public class MultiAasAnonymity extends MultiAnonymity {
    protected final Map<Dimension, Integer> addingAnonymityMap;

    public MultiAasAnonymity(Map<Dimension, Integer> anonymities, Map<Dimension, Integer> addingAnonymityMap) {
        super(anonymities);
        this.addingAnonymityMap = addingAnonymityMap;
    }

    public Map<Dimension, Integer> getAddingAnonymityMap() {
        return addingAnonymityMap;
    }
}
