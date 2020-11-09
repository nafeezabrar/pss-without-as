package pss.data.ioc_table;

import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;

public abstract class IocRow<TAnonymizedOoiIdSet extends AnonymizedLocalOoiSet> {
    public abstract void incrementCount(TAnonymizedOoiIdSet anonymizedOoiIdSet);

    abstract IocRow cloneIocRow();
}
