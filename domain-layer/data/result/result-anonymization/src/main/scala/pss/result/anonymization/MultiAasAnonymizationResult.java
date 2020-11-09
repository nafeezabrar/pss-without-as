package pss.result.anonymization;

import pss.data.oc_table.MultiOcTable;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

public class MultiAasAnonymizationResult extends AasAnonymizationResult<MultiOcTable, MultiAnonymizedLocalOoiSet> {

    public MultiAasAnonymizationResult(MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet, MultiOcTable ocTable) {
        super(anonymizedLocalOoiSet, ocTable);
    }
}
