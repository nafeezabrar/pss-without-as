package pss.report.observed;

import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.report.common.ReportData;

public abstract class ObservedReportData<TLocalOoiCombination extends LocalOoiCombination> extends ReportData {
    protected final int userId;
    protected final TLocalOoiCombination ooiIdCombination;

    protected ObservedReportData(int userId, TLocalOoiCombination ooiIdCombination) {
        this.userId = userId;
        this.ooiIdCombination = ooiIdCombination;
    }

    public int getUserId() {
        return userId;
    }

    public TLocalOoiCombination getLocalOoiCombination() {
        return ooiIdCombination;
    }

    @Override
    public String toString() {
        return String.format("ObservedReportData{userId=%d, ooiIdCombination=%s}", userId, ooiIdCombination);
    }
}
