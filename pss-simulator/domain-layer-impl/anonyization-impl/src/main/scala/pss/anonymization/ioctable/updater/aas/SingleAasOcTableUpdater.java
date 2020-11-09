package pss.anonymization.ioctable.updater.aas;

import pss.anonymization.AasIocTableUpdatable;
import pss.data.anonymity.SingleAasAnonymity;
import pss.data.oc_table.OcCell;
import pss.data.oc_table.SingleOcRow;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.library.MyRandom;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;

import java.util.*;

public class SingleAasOcTableUpdater implements AasIocTableUpdatable<SingleAnonymizedLocalOoiSet, SingleAasAnonymity> {
    protected final SingleOcTable ocTable;
    protected final SingleLocalOoiCollection localOoiCollection;
    protected final MyRandom myRandom;

    public SingleAasOcTableUpdater(SingleOcTable ocTable, SingleLocalOoiCollection localOoiCollection, MyRandom myRandom) {
        this.ocTable = ocTable;
        this.localOoiCollection = localOoiCollection;
        this.myRandom = myRandom;
    }


    @Override
    public SingleAnonymizedLocalOoiSet getAnonymizedOois(Value value, SingleAasAnonymity anonymity) {
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
        Set<Integer> anonymizedLocalOoiSet = new HashSet<>();
        ArrayList<Integer> orderedOoiId = new ArrayList<>();
        List<Integer> ooiIdWithMaxOcCount = new ArrayList<>();
        if (ocRowMap.containsKey(value)) {
            SingleOcRow singleOcRow = ocRowMap.get(value);
            Map<Integer, OcCell> ocCellMap = singleOcRow.getOcCellMap();
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
        for (int localOoi : localOoiCollection.getLocalOoiSet()) {
            if (!orderedOoiId.contains(localOoi)) {
                orderedOoiId.add(localOoi);
            }
        }
        if (ooiIdWithMaxOcCount.size() != 0) {

            int index = myRandom.nextInt(ooiIdWithMaxOcCount.size());
            anonymizedLocalOoiSet.add(orderedOoiId.get(index));
        }

        int size = orderedOoiId.size();

        int addingAnonymity = anonymity.getAddingAnonymity();
        int totalAnonymity = anonymity.getValue();
        int remaining = totalAnonymity - addingAnonymity;
        remaining -= anonymizedLocalOoiSet.size();
        int index = size - 1;
        for (int i = 0; i < remaining; i++) {
            anonymizedLocalOoiSet.add(orderedOoiId.get(index));
            index--;
        }
        return new SingleAnonymizedLocalOoiSet(anonymizedLocalOoiSet);
    }

    @Override
    public void update(Value value, SingleAnonymizedLocalOoiSet singleAnonymizedLocalOoiSet) {
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
        if (!ocRowMap.containsKey(value)) {
            SingleOcRow ocRow = new SingleOcRow(value);
            ocRowMap.put(value, ocRow);
        }
        SingleOcRow ocRow = ocRowMap.get(value);
        Map<Integer, OcCell> ocCellMap = ocRow.getOcCellMap();
        for (int localOoi : singleAnonymizedLocalOoiSet.getAnonymizedIdSet()) {
            if (ocCellMap.containsKey(localOoi)) {
                ocCellMap.get(localOoi).incrementCount();
            } else {
                ocCellMap.put(localOoi, new OcCell(localOoi));
                ocCellMap.get(localOoi).incrementCount();
            }
        }

    }
}
