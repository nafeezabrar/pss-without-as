package pss.anonymization.ioctable.updater.ras;

import pss.anonymization.AasIocTableUpdatable;
import pss.data.anonymity.MultiRasAnonymity;
import pss.data.dimension.Dimension;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.library.MyRandom;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

import java.util.*;

public class MultiRasOcTableUpdater implements AasIocTableUpdatable<MultiAnonymizedLocalOoiSet, MultiRasAnonymity> {
    protected final MultiOcTable ocTable;
    protected final MultiLocalOoiCollection localOoiCollection;
    protected final MyRandom myRandom;

    public MultiRasOcTableUpdater(MultiOcTable ocTable, MultiLocalOoiCollection localOoiCollection, MyRandom myRandom) {
        this.ocTable = ocTable;
        this.localOoiCollection = localOoiCollection;
        this.myRandom = myRandom;
    }


    @Override
    public MultiAnonymizedLocalOoiSet getAnonymizedOois(Value value, MultiRasAnonymity anonymity) {
        Map<Dimension, Set<Integer>> anonymizedLocalOoiSet = new HashMap<>();
        for (Dimension dimension : ocTable.getDimensions()) {
            anonymizedLocalOoiSet.put(dimension, getAnonymizedOoisForOneDimension(dimension, value, anonymity));
        }
        return new MultiAnonymizedLocalOoiSet(anonymizedLocalOoiSet);
    }

    private Set<Integer> getAnonymizedOoisForOneDimension(Dimension dimension, Value value, MultiRasAnonymity anonymity) {
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        Set<Integer> anonymizedLocalOoiSet = new HashSet<>();
        ArrayList<Integer> orderedOoiId = new ArrayList<>();
        List<Integer> ooiIdWithMaxOcCount = new ArrayList<>();
        if (ocRowMap.containsKey(value)) {
            MultiOcRow multiOcRow = ocRowMap.get(value);
            Map<Integer, OcCell> ocCellMap = multiOcRow.getOcCellMap().get(dimension);
            Collection<OcCell> values = ocCellMap.values();
            ArrayList<OcCell> orderedOcCellList = new ArrayList<>(values);
            Collections.sort(orderedOcCellList, new Comparator<OcCell>() {
                @Override
                public int compare(OcCell o1, OcCell o2) {
                    return Integer.compare(o2.getOcCount(), o1.getOcCount());
                }
            });

            OcCell ocCellWithMaxOcCount = orderedOcCellList.get(0);
            int maxOcCount = ocCellWithMaxOcCount.getOcCount();
            for (OcCell ocCell : orderedOcCellList) {
                orderedOoiId.add(ocCell.getOoiId());
                if (ocCell.getOcCount() == maxOcCount) {
                    ooiIdWithMaxOcCount.add(ocCell.getOoiId());
                }
            }

        }
        for (int localOoi : localOoiCollection.getLocalOoiMap().get(dimension)) {
            if (!orderedOoiId.contains(localOoi)) {
                orderedOoiId.add(localOoi);
            }
        }
        if (ooiIdWithMaxOcCount.size() != 0) {

            int index = myRandom.nextInt(ooiIdWithMaxOcCount.size());
            anonymizedLocalOoiSet.add(orderedOoiId.get(index));
        }

        int size = orderedOoiId.size();

        int remaining = anonymity.getAnonymityMap().get(dimension);
        remaining -= anonymizedLocalOoiSet.size();
        int index = size - 1;
        for (int i = 0; i < remaining; i++) {
            anonymizedLocalOoiSet.add(orderedOoiId.get(index));
            index--;
        }
        return anonymizedLocalOoiSet;
    }

    @Override
    public void update(Value value, MultiAnonymizedLocalOoiSet multiAnonymizedLocalOoiSet) {
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        if (!ocRowMap.containsKey(value)) {
            MultiOcRow ocRow = new MultiOcRow(value, localOoiCollection.getLocalOoiMap().keySet());
            ocRowMap.put(value, ocRow);
        }
        MultiOcRow ocRow = ocRowMap.get(value);
        Map<Dimension, Map<Integer, OcCell>> ocCellMaps = ocRow.getOcCellMap();
        for (Dimension dimension : ocCellMaps.keySet()) {
            Map<Integer, OcCell> ocCellMap = ocCellMaps.get(dimension);
            for (int localOoi : multiAnonymizedLocalOoiSet.getAnonymizedOoiSets().get(dimension)) {
                if (ocCellMap.containsKey(localOoi)) {
                    ocCellMap.get(localOoi).incrementCount();
                } else {
                    ocCellMap.put(localOoi, new OcCell(localOoi));
                    ocCellMap.get(localOoi).incrementCount();
                }
            }
        }

    }
}
