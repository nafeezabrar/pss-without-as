package pss.data.oc_table;

import pss.data.dimension.Dimension;
import pss.data.valuemap.Value;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MultiOcRow extends OcRow implements Serializable {
    private static long serialVersionUID = 1L;
    protected Map<Dimension, Map<Integer, OcCell>> ocCellMap;
    private Set<Dimension> dimensionSet;

    public MultiOcRow(Value value, Set<Dimension> dimensionSet) {
        super(value);
        this.dimensionSet = dimensionSet;
        this.ocCellMap = new HashMap<>();
        for (Dimension dimension : dimensionSet) {
            ocCellMap.put(dimension, new HashMap<>());
        }
    }

    public Map<Dimension, Map<Integer, OcCell>> getOcCellMap() {
        return ocCellMap;
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.writeInt(totalReportCount);
        stream.writeObject(value);
        List<Dimension> dimensions = new LinkedList<>(dimensionSet);
        stream.writeObject(dimensions);
        for (Dimension dimension : dimensions) {
            Map<Integer, OcCell> ocCells = this.ocCellMap.get(dimension);
            LinkedList<Integer> ooiIds = new LinkedList<>(ocCells.keySet());
            stream.writeObject(ooiIds);
            for (Integer ooiId : ooiIds) {
                OcCell ocCell = ocCells.get(ooiId);
                stream.writeObject(ocCell);
            }
        }
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        totalReportCount = stream.readInt();
        value = (Value) stream.readObject();
        List<Dimension> dimensionList = (List<Dimension>) stream.readObject();
        dimensionSet = new HashSet<>(dimensionList);
        ocCellMap = new HashMap<>();
        for (Dimension dimension : dimensionSet) {
            ocCellMap.put(dimension, new HashMap<>());
        }
        for (Dimension dimension : dimensionList) {
            Map<Integer, OcCell> ocCells = this.ocCellMap.get(dimension);
            LinkedList<Integer> ooiIds = (LinkedList<Integer>) stream.readObject();
            for (Integer ooiId : ooiIds) {
                OcCell ocCell = (OcCell) stream.readObject();
                ocCells.put(ooiId, ocCell);
            }
        }
    }

    @Override
    MultiOcRow cloneOcRow() {
        MultiOcRow clonedOcRow = new MultiOcRow(value, dimensionSet);
        Map<Dimension, Map<Integer, OcCell>> clonedOcRowMap = clonedOcRow.getOcCellMap();
        for (Dimension dimension : this.ocCellMap.keySet()) {
            Map<Integer, OcCell> ocCellMap = this.ocCellMap.get(dimension);
            Map<Integer, OcCell> clonedOcCellMap = cloneOcCellMap(ocCellMap);
            clonedOcRowMap.put(dimension, clonedOcCellMap);
        }
        clonedOcRow.setTotalReportCount(totalReportCount);
        return clonedOcRow;
    }

    private Map<Integer, OcCell> cloneOcCellMap(Map<Integer, OcCell> ocCellMap) {
        Map<Integer, OcCell> clonedOcCellMap = new HashMap<>();
        for (Integer value : ocCellMap.keySet()
                ) {
            OcCell ocCell = ocCellMap.get(value);
            clonedOcCellMap.put(value, ocCell.cloneOcCell());
        }
        return clonedOcCellMap;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((MultiOcRow) that);
    }

    private boolean equals(MultiOcRow that) {
        return super.equals(that) && Objects.equals(this.dimensionSet, that.dimensionSet)
                && Objects.equals(this.ocCellMap, that.ocCellMap);
    }

    @Override
    public int hashCode() {
        return 34579 ^ super.hashCode() ^ Objects.hashCode(dimensionSet) ^ Objects.hashCode(ocCellMap);
    }
}
