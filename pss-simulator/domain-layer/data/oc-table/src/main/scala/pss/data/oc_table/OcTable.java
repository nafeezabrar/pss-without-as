package pss.data.oc_table;

import pss.data.valuemap.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class OcTable<TOcRow extends OcRow> {

    protected Map<Value, TOcRow> ocRowMap;

    public OcTable() {
        this.ocRowMap = new HashMap<>();
    }

    public abstract OcTable cloneOcTable();

    public Map<Value, TOcRow> getOcRowMap() {
        return ocRowMap;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((OcTable<TOcRow>) that);
    }

    private boolean equals(OcTable<TOcRow> that) {
        return Objects.equals(this.ocRowMap, that.ocRowMap);
    }

    @Override
    public int hashCode() {
        return 78953 ^ Objects.hashCode(ocRowMap);
    }

    @Override
    public String toString() {
        return "OcTable{" +
                "ocRowMap=" + ocRowMap +
                '}';
    }
}
