package pss.data.ooi.combination;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;

import java.util.Map;
import java.util.Objects;

public class MultiOoiCombination extends OoiCombination {
    protected final Map<Dimension, Ooi> ooiMap;

    public MultiOoiCombination(Map<Dimension, Ooi> ooiMap) {
        this.ooiMap = ooiMap;
    }

    public Map<Dimension, Ooi> getOoiMap() {
        return ooiMap;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((MultiOoiCombination) that);
    }

    private boolean equals(MultiOoiCombination that) {
        return Objects.equals(this.ooiMap, that.ooiMap);
    }

    @Override
    public int hashCode() {
        return 34785 ^ Objects.hashCode(ooiMap);
    }
}
