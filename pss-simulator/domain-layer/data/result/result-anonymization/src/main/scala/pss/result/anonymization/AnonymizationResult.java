package pss.result.anonymization;

import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;
import pss.result.Result;

public abstract class AnonymizationResult<TAnonymizedLocalOoiSet extends AnonymizedLocalOoiSet> extends Result {
    protected final TAnonymizedLocalOoiSet anonymizedLocalOoiSet;

    protected AnonymizationResult(TAnonymizedLocalOoiSet anonymizedLocalOoiSet) {
        this.anonymizedLocalOoiSet = anonymizedLocalOoiSet;
    }

    public TAnonymizedLocalOoiSet getAnonymizedLocalOoiSet() {
        return anonymizedLocalOoiSet;
    }
}
