package pss.data.ooi.local.collection;

import java.util.Objects;
import java.util.Set;

public class SingleLocalOoiCollection extends LocalOoiCollection {
    protected final Set<Integer> localOoiSet;

    public SingleLocalOoiCollection(Set<Integer> localOoiSet) {
        this.localOoiSet = localOoiSet;
    }

    public Set<Integer> getLocalOoiSet() {
        return localOoiSet;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((SingleLocalOoiCollection) that);
    }

    private boolean equals(SingleLocalOoiCollection that) {
        return Objects.equals(this.localOoiSet, that.localOoiSet);
    }

    @Override
    public int hashCode() {
        return 75856 ^ Objects.hashCode(localOoiSet);
    }
}
