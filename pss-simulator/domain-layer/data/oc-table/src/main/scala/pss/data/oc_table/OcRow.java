package pss.data.oc_table;

import pss.data.valuemap.Value;

import java.util.Objects;

public abstract class OcRow {
    private static long serialVersionUID = 1L;
    protected Value value;
    protected int totalReportCount;

    protected OcRow() {
    }
    protected OcRow(Value value) {
        this.value = value;
        this.totalReportCount = 0;
    }

    abstract OcRow cloneOcRow();

    public int getTotalReportCount() {
        return totalReportCount;
    }

    public void setTotalReportCount(int totalReportCount) {
        this.totalReportCount = totalReportCount;
    }

    public void incrementTotalReportCount() {
        this.totalReportCount++;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((OcRow) that);
    }

    private boolean equals(OcRow that) {
        return Objects.equals(this.value, that.value) && this.totalReportCount == that.totalReportCount;
    }

    @Override
    public int hashCode() {
        return 99631 ^ Objects.hashCode(value) ^ totalReportCount;
    }

    @Override
    public String toString() {
        return "OcRow{" +
                "value=" + value +
                ", totalReportCount=" + totalReportCount +
                '}';
    }
}
