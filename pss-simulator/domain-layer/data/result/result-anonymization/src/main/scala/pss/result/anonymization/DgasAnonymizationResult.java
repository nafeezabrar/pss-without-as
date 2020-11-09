package pss.result.anonymization;

import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;

public class DgasAnonymizationResult<TAnonymizedLocalOoiSet extends AnonymizedLocalOoiSet> extends AnonymizationResult<TAnonymizedLocalOoiSet> {


    protected DgasAnonymizationResult(TAnonymizedLocalOoiSet anonymizedLocalOoiSet) {
        super(anonymizedLocalOoiSet);
    }
}
