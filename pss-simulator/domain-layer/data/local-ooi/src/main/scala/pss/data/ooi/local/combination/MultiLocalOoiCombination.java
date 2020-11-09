package pss.data.ooi.local.combination;

import pss.data.dimension.Dimension;

import java.util.Map;
import java.util.Objects;

public class MultiLocalOoiCombination extends LocalOoiCombination {
    protected final Map<Dimension, Integer> localOoiMap;

    public MultiLocalOoiCombination(Map<Dimension, Integer> localOoiMap) {
        this.localOoiMap = localOoiMap;
    }

    public Map<Dimension, Integer> getLocalOoiMap() {
        return localOoiMap;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((MultiLocalOoiCombination) that);
    }

    private boolean equals(MultiLocalOoiCombination that) {
        return Objects.equals(this.localOoiMap, that.localOoiMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(localOoiMap);
    }
}
