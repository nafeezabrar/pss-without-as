package pss.report.finalreport;

import pss.data.valuemap.Value;
import pss.report.common.Report;

public abstract class FinalReport<TReportData extends FinalReportData>
        extends Report<TReportData> {

    public FinalReport(int id, Value value, TReportData data) {
        super(id, value, data);
    }

    public TReportData getReportData() {
        return data;
    }
}
