package pss.data.oc_table;

import pss.data.dimension.Dimension;
import pss.data.valuemap.Value;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MultiOcTable extends OcTable<MultiOcRow> implements Serializable {
    private static long serialVersionUID = -3383L;
    private int totalDimensions;
    private Set<Dimension> dimensions;

    public MultiOcTable(int totalDimensions, Set<Dimension> dimensions) {
        this.totalDimensions = totalDimensions;
        this.dimensions = dimensions;
    }

    public int getTotalDimensions() {
        return totalDimensions;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.writeObject(totalDimensions);
        List<Dimension> dimensionList = new LinkedList<>(dimensions);
        stream.writeObject(dimensionList);
        LinkedList<Value> values = new LinkedList<>(ocRowMap.keySet());
        stream.writeObject(values);
        for (Value value : values) {
            MultiOcRow multiOcRow = ocRowMap.get(value);
            stream.writeObject(multiOcRow);
        }
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        totalDimensions = (int) stream.readObject();
        LinkedList<Dimension> dimensionList = (LinkedList<Dimension>) stream.readObject();
        dimensions = new HashSet<>(dimensionList);
        ocRowMap = new HashMap<>();
        LinkedList<Value> entryset = (LinkedList<Value>) stream.readObject();
        for (Value value : entryset) {
            MultiOcRow ocRow = (MultiOcRow) stream.readObject();
            ocRowMap.put(value, ocRow);
        }
    }

    @Override
    public MultiOcTable cloneOcTable() {
        MultiOcTable clonedOcTable = new MultiOcTable(totalDimensions, dimensions);
        Map<Value, MultiOcRow> clonedOcRowMap = clonedOcTable.getOcRowMap();
        for (Value value : ocRowMap.keySet()) {
            MultiOcRow multiOcRow = ocRowMap.get(value);
            MultiOcRow clonedOcRow = multiOcRow.cloneOcRow();
            clonedOcRowMap.put(value, clonedOcRow);
        }
        return clonedOcTable;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((MultiOcTable) that);
    }

    private boolean equals(MultiOcTable that) {
        return super.equals(that) && this.totalDimensions == that.totalDimensions &&
                Objects.equals(this.dimensions, that.dimensions);
    }

    @Override
    public int hashCode() {
        return 46879 ^ super.hashCode() ^ totalDimensions ^ Objects.hashCode(dimensions);
    }
}
