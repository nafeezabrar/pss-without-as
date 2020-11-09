package pss.report.finalreport;

import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.report.common.ReportData;

public abstract class FinalReportData<TLocalOoiCollection extends LocalOoiCollection> extends ReportData {
    protected final int userId;
    protected final TLocalOoiCollection ooiIdCollection;

    protected FinalReportData(int userId, TLocalOoiCollection ooiIdCollection) {
        this.userId = userId;
        this.ooiIdCollection = ooiIdCollection;
    }

    public TLocalOoiCollection getLocalOoiCollection() {
        return ooiIdCollection;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format("FinalReportData{userId=%d, ooiIdCollection=%s}", userId, ooiIdCollection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalReportData<?> that = (FinalReportData<?>) o;

        if (userId != that.userId) return false;
        return ooiIdCollection != null ? ooiIdCollection.equals(that.ooiIdCollection) : that.ooiIdCollection == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (ooiIdCollection != null ? ooiIdCollection.hashCode() : 0);
        return result;
    }
}
