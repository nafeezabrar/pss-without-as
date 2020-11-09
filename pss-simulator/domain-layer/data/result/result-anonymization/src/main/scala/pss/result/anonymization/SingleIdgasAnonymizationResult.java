package pss.result.anonymization;

import pss.data.ioc_table.SingleIocTable;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;

public class SingleIdgasAnonymizationResult extends IdgasAnonymizationResult<SingleIocTable, SingleAnonymizedLocalOoiSet> {

    public SingleIdgasAnonymizationResult(SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet, SingleIocTable iocTable) {
        super(anonymizedLocalOoiSet, iocTable);
    }
}
