package pss.result.anonymization;

import pss.data.oc_table.MultiOcTable;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

public class MultiRasAnonymizationResult extends AasAnonymizationResult<MultiOcTable, MultiAnonymizedLocalOoiSet> {

    public MultiRasAnonymizationResult(MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet, MultiOcTable ocTable) {
        super(anonymizedLocalOoiSet, ocTable);
    }
}
