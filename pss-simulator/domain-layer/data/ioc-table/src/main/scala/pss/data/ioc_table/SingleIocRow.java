package pss.data.ioc_table;

import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;

import java.util.HashSet;
import java.util.Set;

public class SingleIocRow extends IocRow<SingleAnonymizedLocalOoiSet> {

    protected Set<IocCell> iocCells;

    public SingleIocRow(Set<IocCell> iocCells) {
        this.iocCells = iocCells;
    }

    @Override
    public void incrementCount(SingleAnonymizedLocalOoiSet singleAnonymizedOoiIdSet) {
        Set<Integer> anonymizedOoiIds = singleAnonymizedOoiIdSet.getAnonymizedIdSet();
        for (IocCell iocCell : iocCells) {
            if (!anonymizedOoiIds.contains(iocCell.ooiId))
                iocCell.iocCount++;
        }
    }

    @Override
    SingleIocRow cloneIocRow() {
        Set<IocCell> clonedIocCells = new HashSet<>();
        for (IocCell iocCell : iocCells) {
            clonedIocCells.add(iocCell.cloneIocCell());
        }
        return new SingleIocRow(clonedIocCells);
    }

    public Set<IocCell> getIocCells() {
        return iocCells;
    }
}
