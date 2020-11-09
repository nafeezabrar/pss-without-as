package pss.local.ooi.anonymized;

import pss.data.dimension.Dimension;

import java.util.Map;
import java.util.Set;

public class MultiAnonymizedLocalOoiSet extends AnonymizedLocalOoiSet {
    protected final Map<Dimension, Set<Integer>> anonymizedOoiSets;


    public MultiAnonymizedLocalOoiSet(Map<Dimension, Set<Integer>> anonymizedOoiSets) {
        this.anonymizedOoiSets = anonymizedOoiSets;
    }

    public Map<Dimension, Set<Integer>> getAnonymizedOoiSets() {
        return anonymizedOoiSets;
    }
}
