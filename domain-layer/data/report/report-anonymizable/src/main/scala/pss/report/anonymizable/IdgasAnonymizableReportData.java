package pss.report.anonymizable;

import pss.data.anonymity.Anonymity;
import pss.data.ooi.local.combination.LocalOoiCombination;

public class IdgasAnonymizableReportData<TLocalOoiCombination extends LocalOoiCombination, TAnonymity extends Anonymity> extends AnonymizableReportData<TAnonymity> {
    protected final TLocalOoiCombination localOoiCombination;

    public IdgasAnonymizableReportData(TAnonymity anonymity, TLocalOoiCombination localOoiCombination) {
        super(anonymity);
        this.localOoiCombination = localOoiCombination;
    }

    public TLocalOoiCombination getLocalOoiCombination() {
        return localOoiCombination;
    }

    @Override
    public String toString() {
        return String.format("AnonymizableReportData{ooiIdCombination=%s, anonymity=%s}", localOoiCombination, anonymity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdgasAnonymizableReportData<?, ?> that = (IdgasAnonymizableReportData<?, ?>) o;

        if (localOoiCombination != null ? !localOoiCombination.equals(that.localOoiCombination) : that.localOoiCombination != null)
            return false;
        return anonymity != null ? anonymity.equals(that.anonymity) : that.anonymity == null;

    }

    @Override
    public int hashCode() {
        int result = localOoiCombination != null ? localOoiCombination.hashCode() : 0;
        result = 31 * result + (anonymity != null ? anonymity.hashCode() : 0);
        return result;
    }
}
