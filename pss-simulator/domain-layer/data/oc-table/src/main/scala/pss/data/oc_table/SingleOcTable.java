package pss.data.oc_table;

import pss.data.valuemap.Value;

import java.io.Serializable;
import java.util.Map;

public class SingleOcTable extends OcTable<SingleOcRow> implements Serializable {
    private static long serialVersionUID = 1L;
    @Override
    public SingleOcTable cloneOcTable() {
        SingleOcTable clonedOcTable = new SingleOcTable();
        Map<Value, SingleOcRow> clonedOcRowMap = clonedOcTable.getOcRowMap();
        for (Value value : this.ocRowMap.keySet()) {
            SingleOcRow singleOcRow = this.ocRowMap.get(value);
            clonedOcRowMap.put(value, singleOcRow.cloneOcRow());
        }
        return clonedOcTable;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((SingleOcTable) that);
    }

    private boolean equals(SingleOcTable that) {
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        return 19679 ^ super.hashCode();
    }

}
