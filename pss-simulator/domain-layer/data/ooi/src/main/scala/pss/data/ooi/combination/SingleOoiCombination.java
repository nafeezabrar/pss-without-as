package pss.data.ooi.combination;

import pss.data.ooi.Ooi;

import java.util.Objects;

public class SingleOoiCombination extends OoiCombination {
    protected final Ooi ooi;

    public SingleOoiCombination(Ooi ooi) {
        this.ooi = ooi;
    }

    public Ooi getOoi() {
        return ooi;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((SingleOoiCombination) that);
    }

    private boolean equals(SingleOoiCombination that) {
        return Objects.equals(this.ooi, that.ooi);
    }

    @Override
    public int hashCode() {
        return 67165 ^ Objects.hashCode(ooi);
    }
}
