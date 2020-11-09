package pss.anonymization.ioctable.updater.idgas;

import pss.anonymization.IdgasIocTableUpdatable;
import pss.data.anonymity.SingleAnonymity;
import pss.data.ioc_table.IocCell;
import pss.data.ioc_table.SingleIocRow;
import pss.data.ioc_table.SingleIocTable;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;

import java.util.*;

public class IdgasSingleIdgasIocTableUpdater implements IdgasIocTableUpdatable<SingleAnonymizedLocalOoiSet, SingleLocalOoiCombination, SingleAnonymity> {
    protected final SingleIocTable iocTable;

    public IdgasSingleIdgasIocTableUpdater(SingleIocTable iocTable) {
        this.iocTable = iocTable;
    }

    @Override
    public SingleAnonymizedLocalOoiSet getAnonymizedOois(SingleLocalOoiCombination localOoiCombination, SingleAnonymity anonymity) {
        Map<SingleLocalOoiCombination, SingleIocRow> iocMap = iocTable.getIocMap();
        SingleIocRow iocRow = iocMap.get(localOoiCombination);
        Set<IocCell> iocCells = iocRow.getIocCells();
        ArrayList<IocCell> iocCellList = new ArrayList<>(iocCells);

        Collections.sort(iocCellList, (o1, o2) -> {
            boolean o1Decoded = iocTable.getDecodedOoiIds().contains(o1.getOoiId());
            boolean o2Decoded = iocTable.getDecodedOoiIds().contains(o2.getOoiId());
            if (o1Decoded && o2Decoded) return 0;
            if (o1Decoded) return -1;
            if (o2Decoded) return 1;
            return Integer.compare(o2.getIocCount(), o1.getIocCount());
        });

        Set<Integer> anonymizedOoiSet = new HashSet<>();
        anonymizedOoiSet.add(localOoiCombination.getLocalOoi());
        for (int i = 0; i < anonymity.getValue() - 1; i++) {
            anonymizedOoiSet.add(iocCellList.get(i).getOoiId());
        }
        return new SingleAnonymizedLocalOoiSet(anonymizedOoiSet);
    }

    @Override
    public void update(SingleLocalOoiCombination localOoiCombination, SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet) {
        Map<SingleLocalOoiCombination, SingleIocRow> iocMap = iocTable.getIocMap();
        SingleIocRow iocRow = iocMap.get(localOoiCombination);
        iocRow.incrementCount(anonymizedLocalOoiSet);
        Set<Integer> decodedOoiIds = iocTable.getDecodedOoiIds();
        if (iocTable.isDecoded(iocRow)) {
            decodedOoiIds.add(localOoiCombination.getLocalOoi());
        }
    }
}
