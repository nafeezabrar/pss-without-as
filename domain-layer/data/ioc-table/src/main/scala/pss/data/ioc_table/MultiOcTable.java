package pss.data.ioc_table;

import pss.data.anonymity.MultiAnonymity;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

import java.util.HashMap;
import java.util.Map;

public class MultiOcTable extends IocTable<MultiIocRow, MultiAnonymizedLocalOoiSet, MultiLocalOoiCombination, MultiAnonymity> {

    public MultiOcTable(int totalOois, Map<MultiLocalOoiCombination, MultiIocRow> iocMap) {
        super(totalOois, iocMap);
    }

    @Override
    public MultiOcTable cloneIocTable() {
        Map<MultiLocalOoiCombination, MultiIocRow> clonedIocRowMap = new HashMap<>();
        for (MultiLocalOoiCombination ooiCombination : iocMap.keySet()) {
            clonedIocRowMap.put(ooiCombination, iocMap.get(ooiCombination).cloneIocRow());
        }
        return new MultiOcTable(this.totalOois, clonedIocRowMap);
    }
}
