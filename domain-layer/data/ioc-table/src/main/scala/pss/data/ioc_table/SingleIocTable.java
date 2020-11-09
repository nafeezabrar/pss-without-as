package pss.data.ioc_table;

import pss.data.anonymity.SingleAnonymity;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SingleIocTable extends IocTable<SingleIocRow, SingleAnonymizedLocalOoiSet, SingleLocalOoiCombination, SingleAnonymity> {

    protected Set<Integer> decodedOoiIds;

    public SingleIocTable(int totalOois, Map<SingleLocalOoiCombination, SingleIocRow> iocMap) {
        super(totalOois, iocMap);
        this.decodedOoiIds = new HashSet<>();
    }

    @Override
    public SingleIocTable cloneIocTable() {
        Map<SingleLocalOoiCombination, SingleIocRow> clonedIocMap = new HashMap<>();
        for (SingleLocalOoiCombination localOoiCombination : iocMap.keySet()) {
            clonedIocMap.put(localOoiCombination, iocMap.get(localOoiCombination).cloneIocRow());
        }
        return new SingleIocTable(this.totalOois, clonedIocMap);
    }

    public boolean isDecoded(SingleIocRow iocRow) {
        boolean isDecoded = true;
        for (IocCell iocCell : iocRow.iocCells) {
            if (iocCell.iocCount <= 0) {
                isDecoded = false;
                break;
            }
        }
        return isDecoded;
    }

    public Set<Integer> getDecodedOoiIds() {
        return decodedOoiIds;
    }
}
