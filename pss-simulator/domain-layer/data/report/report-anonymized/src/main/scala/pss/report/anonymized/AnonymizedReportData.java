package pss.report.anonymized;

import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.report.common.ReportData;

public abstract class AnonymizedReportData<TLocalOoiCollection extends LocalOoiCollection> extends ReportData {
    protected final TLocalOoiCollection ooiIdCollection;

    protected AnonymizedReportData(TLocalOoiCollection localOoiCollection) {
        this.ooiIdCollection = localOoiCollection;
    }

    public TLocalOoiCollection getLocalOoiCollection() {
        return ooiIdCollection;
    }

    @Override
    public String toString() {
        return String.format("AnonymizedReportData{ooiIdCollection=%s}", ooiIdCollection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnonymizedReportData<?> that = (AnonymizedReportData<?>) o;

        return ooiIdCollection != null ? ooiIdCollection.equals(that.ooiIdCollection) : that.ooiIdCollection == null;

    }

    @Override
    public int hashCode() {
        return ooiIdCollection != null ? ooiIdCollection.hashCode() : 0;
    }
}
