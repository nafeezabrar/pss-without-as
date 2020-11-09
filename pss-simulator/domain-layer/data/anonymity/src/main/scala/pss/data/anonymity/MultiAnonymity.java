package pss.data.anonymity;

import pss.data.dimension.Dimension;

import java.util.Map;

public class MultiAnonymity extends Anonymity {
    protected final Map<Dimension, Integer> anonymityMap;

    public MultiAnonymity(Map<Dimension, Integer> anonymities) {
        this.anonymityMap = anonymities;
    }

    public Map<Dimension, Integer> getAnonymityMap() {
        return anonymityMap;
    }
}
