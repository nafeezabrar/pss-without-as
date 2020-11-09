package pss.anonymization.ioctable.generation;

import pss.data.ioc_table.IocCell;
import pss.data.ioc_table.SingleIocRow;
import pss.data.ioc_table.SingleIocTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SingleIocTableGenerator implements IocTableGenerator<SingleIocTable, SingleLocalOoiCollection> {

    @Override
    public SingleIocTable generateIocTable(SingleLocalOoiCollection localOoiCollection) {
        Set<Integer> ooiIdSet = localOoiCollection.getLocalOoiSet();
        Map<SingleLocalOoiCombination, SingleIocRow> iocMap = new HashMap<>();
        for (Integer ooiId : ooiIdSet) {
            iocMap.put(new SingleLocalOoiCombination(ooiId), generateSingleIocRow(ooiId, ooiIdSet));
        }
        return new SingleIocTable(ooiIdSet.size(), iocMap);
    }

    private SingleIocRow generateSingleIocRow(int realOoiId, Set<Integer> allOoiIds) {
        Set<IocCell> iocCells = new HashSet<>();
        for (Integer ooiId : allOoiIds) {
            if (ooiId != realOoiId) {
                iocCells.add(new IocCell(ooiId));
            }
        }
        return new SingleIocRow(iocCells);
    }
}
