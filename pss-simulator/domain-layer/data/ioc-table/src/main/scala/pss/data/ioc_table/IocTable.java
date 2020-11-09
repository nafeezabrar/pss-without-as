package pss.data.ioc_table;

import pss.data.anonymity.Anonymity;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;

import java.util.Map;

public abstract class IocTable<TIocRow extends IocRow,
        TAnonymizedOois extends AnonymizedLocalOoiSet, TLocalOoiCombination extends LocalOoiCombination,
        TAnonymity extends Anonymity> {
    protected final int totalOois;
    protected Map<TLocalOoiCombination, TIocRow> iocMap;

    public IocTable(int totalOois, Map<TLocalOoiCombination, TIocRow> iocMap) {
        this.totalOois = totalOois;
        this.iocMap = iocMap;
    }

    public int getTotalOois() {
        return totalOois;
    }

    public abstract IocTable cloneIocTable();

    public Map<TLocalOoiCombination, TIocRow> getIocMap() {
        return iocMap;
    }
}