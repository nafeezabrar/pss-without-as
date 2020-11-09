package pss.result.anonymization;

import pss.data.ioc_table.MultiOcTable;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

public class MultiAnonymizationResult extends IdgasAnonymizationResult<MultiOcTable, MultiAnonymizedLocalOoiSet> {

    public MultiAnonymizationResult(MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet, MultiOcTable iocTable) {
        super(anonymizedLocalOoiSet, iocTable);
    }
}
