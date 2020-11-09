package pss.result.anonymization;

import pss.data.oc_table.SingleOcTable;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;

public class SingleAasAnonymizationResult extends AasAnonymizationResult<SingleOcTable, SingleAnonymizedLocalOoiSet> {

    public SingleAasAnonymizationResult(SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet, SingleOcTable ocTable) {
        super(anonymizedLocalOoiSet, ocTable);
    }
}
