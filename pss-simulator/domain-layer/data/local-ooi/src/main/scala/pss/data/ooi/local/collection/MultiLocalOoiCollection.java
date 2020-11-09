package pss.data.ooi.local.collection;

import pss.data.dimension.Dimension;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MultiLocalOoiCollection extends LocalOoiCollection {
    protected final Map<Dimension, Set<Integer>> localOoiMap;

    public MultiLocalOoiCollection(Map<Dimension, Set<Integer>> ooiIdSets) {
        this.localOoiMap = ooiIdSets;
    }

    public Map<Dimension, Set<Integer>> getLocalOoiMap() {
        return localOoiMap;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((MultiLocalOoiCollection) that);
    }

    private boolean equals(MultiLocalOoiCollection that) {
        return Objects.equals(this.localOoiMap, that.localOoiMap);
    }

    @Override
    public int hashCode() {
        return 73475 ^ Objects.hashCode(localOoiMap);
    }
}
