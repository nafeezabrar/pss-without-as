package pss.data.oc_table;

import pss.data.valuemap.Value;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SingleOcRow extends OcRow implements Serializable {
    private static long serialVersionUID = 1L;
    protected Map<Integer, OcCell> ocCellMap;

    public SingleOcRow(Value value) {
        super(value);
        this.ocCellMap = new HashMap<>();
    }

    @Override
    SingleOcRow cloneOcRow() {
        SingleOcRow clonedOcRow = new SingleOcRow(value);
        for (Integer value : ocCellMap.keySet()
                ) {
            OcCell ocCell = ocCellMap.get(value);
            clonedOcRow.ocCellMap.put(value, ocCell.cloneOcCell());
        }
        clonedOcRow.setTotalReportCount(totalReportCount);

        return clonedOcRow;
    }

    public Map<Integer, OcCell> getOcCellMap() {
        return ocCellMap;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((SingleOcRow) that);
    }

    private boolean equals(SingleOcRow that) {
        return super.equals(that) && Objects.equals(this.ocCellMap, that.ocCellMap);
    }

    @Override
    public int hashCode() {
        return 98472 ^ super.hashCode() ^ Objects.hashCode(ocCellMap);
    }
}
