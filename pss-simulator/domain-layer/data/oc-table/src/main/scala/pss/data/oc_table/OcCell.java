package pss.data.oc_table;

import java.io.Serializable;
import java.util.Objects;

public class OcCell implements Serializable {
    protected final int ooiId;
    protected int ocCount;

    public OcCell(int ooiId) {
        this.ooiId = ooiId;
        this.ocCount = 0;
    }

    public OcCell cloneOcCell() {
        OcCell clonedOcCell = new OcCell(ooiId);
        clonedOcCell.setOcCount(ocCount);
        return clonedOcCell;
    }

    public int getOoiId() {
        return ooiId;
    }

    public int getOcCount() {
        return ocCount;
    }

    public void setOcCount(int ocCount) {
        this.ocCount = ocCount;
    }

    public void incrementCount() {
        this.ocCount++;
    }

    @Override
    public String toString() {
        return "OcCell{" +
                "ooiId=" + ooiId +
                ", ocCount=" + ocCount +
                '}';
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((OcCell) that);
    }

    private boolean equals(OcCell that) {
        return this.ooiId == that.ooiId &&
                Objects.equals(this.ocCount, that.ocCount);
    }

    @Override
    public int hashCode() {
        return 46169 ^ ooiId ^ Objects.hashCode(ocCount);
    }
}
