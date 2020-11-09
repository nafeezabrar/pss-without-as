package pss.data.ooi.local.combination;

public class SingleLocalOoiCombination extends LocalOoiCombination {
    protected final int ooiId;

    public SingleLocalOoiCombination(int ooiId) {
        this.ooiId = ooiId;
    }

    public int getLocalOoi() {
        return ooiId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((SingleLocalOoiCombination) that);
    }

    private boolean equals(SingleLocalOoiCombination that) {
        return this.ooiId == that.ooiId;
    }

    @Override
    public int hashCode() {
        return 48687 ^ ooiId;
    }
}
