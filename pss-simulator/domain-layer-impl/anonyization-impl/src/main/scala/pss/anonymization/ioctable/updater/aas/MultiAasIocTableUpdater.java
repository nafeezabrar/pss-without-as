package pss.anonymization.ioctable.updater.aas;

import pss.anonymization.AasIocTableUpdatable;
import pss.data.anonymity.MultiAasAnonymity;
import pss.data.dimension.Dimension;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.library.MyRandom;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

import java.util.*;

public class MultiAasIocTableUpdater implements AasIocTableUpdatable<MultiAnonymizedLocalOoiSet, MultiAasAnonymity> {
    protected final MultiOcTable ocTable;
    protected final MultiLocalOoiCollection localOoiCollection;
    protected final MyRandom myRandom;

    public MultiAasIocTableUpdater(MultiOcTable ocTable, MultiLocalOoiCollection localOoiCollection, MyRandom myRandom) {
        this.ocTable = ocTable;
        this.localOoiCollection = localOoiCollection;
        this.myRandom = myRandom;
    }


    @Override
    public MultiAnonymizedLocalOoiSet getAnonymizedOois(Value value, MultiAasAnonymity anonymity) {
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        Map<Dimension, Set<Integer>> anonymizedLocalOoiSets = new HashMap<>();
        Map<Dimension, ArrayList<Integer>> orderedOoiIds = new HashMap<>();
        Map<Dimension, List<Integer>> ooiIdWithMaxOcCounts = new HashMap<>();
        for (Dimension dimension : ocTable.getDimensions()) {
            orderedOoiIds.put(dimension, new ArrayList<>());
            ooiIdWithMaxOcCounts.put(dimension, new ArrayList<>());
        }
        if (ocRowMap.containsKey(value)) {

            MultiOcRow multiOcRow = ocRowMap.get(value);
            Map<Dimension, Map<Integer, OcCell>> ocCellMaps = multiOcRow.getOcCellMap();
            for (Dimension dimension : ocCellMaps.keySet()) {
                Map<Integer, OcCell> ocCellMap = ocCellMaps.get(dimension);
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
                    orderedOoiIds.get(dimension).add(ocCell.getOoiId());
                    if (ocCell.getOcCount() == maxOcCount) {
                        ooiIdWithMaxOcCounts.get(dimension).add(ocCell.getOoiId());
                    }
                }
            }

        }
        for (Dimension dimension : localOoiCollection.getLocalOoiMap().keySet()) {
            Set<Integer> anonymizedLocalOoiIds = new HashSet<>();
            Set<Integer> localOoiIdSet = localOoiCollection.getLocalOoiMap().get(dimension);
            for (int localOoi : localOoiIdSet) {
                if (!orderedOoiIds.get(dimension).contains(localOoi)) {
                    orderedOoiIds.get(dimension).add(localOoi);
                }
            }
            if (ooiIdWithMaxOcCounts.get(dimension).size() != 0) {
                int index = myRandom.nextInt(ooiIdWithMaxOcCounts.get(dimension).size());
                anonymizedLocalOoiIds.add(orderedOoiIds.get(dimension).get(index));
            }

            int size = orderedOoiIds.get(dimension).size();
            int addingAnonymity = anonymity.getAddingAnonymityMap().get(dimension);
            int totalAnonymity = anonymity.getAnonymityMap().get(dimension);
            int remaining = totalAnonymity - addingAnonymity;
            remaining -= anonymizedLocalOoiIds.size();
            int index = size - 1;
            for (int i = 0; i < remaining; i++) {
                anonymizedLocalOoiIds.add(orderedOoiIds.get(dimension).get(index));
                index--;
            }
            anonymizedLocalOoiSets.put(dimension, anonymizedLocalOoiIds);
        }
        return new MultiAnonymizedLocalOoiSet(anonymizedLocalOoiSets);
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
