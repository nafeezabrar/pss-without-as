package pss.result.anonymization;

import pss.data.oc_table.SingleOcTable;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;

public class SingleRasAnonymizationResult extends AasAnonymizationResult<SingleOcTable, SingleAnonymizedLocalOoiSet> {

    public SingleRasAnonymizationResult(SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet, SingleOcTable ocTable) {
        super(anonymizedLocalOoiSet, ocTable);
    }
}
