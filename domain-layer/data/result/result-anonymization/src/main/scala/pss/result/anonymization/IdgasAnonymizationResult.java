package pss.result.anonymization;

import pss.data.ioc_table.IocTable;
import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;

public abstract class IdgasAnonymizationResult<TIocTable extends IocTable, TAnonymizedLocalOoiSet extends AnonymizedLocalOoiSet> extends AnonymizationResult<TAnonymizedLocalOoiSet> {
    protected final TIocTable iocTable;

    protected IdgasAnonymizationResult(TAnonymizedLocalOoiSet anonymizedLocalOoiSet, TIocTable iocTable) {
        super(anonymizedLocalOoiSet);
        this.iocTable = iocTable;
    }


    public TIocTable getIocTable() {
        return iocTable;
    }

    public TAnonymizedLocalOoiSet getAnonymizedLocalOoiSet() {
        return anonymizedLocalOoiSet;
    }
}
