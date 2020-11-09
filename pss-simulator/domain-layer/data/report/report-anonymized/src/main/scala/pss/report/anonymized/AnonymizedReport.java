package pss.report.anonymized;

import pss.data.valuemap.Value;
import pss.report.common.Report;

public abstract class AnonymizedReport<TData extends AnonymizedReportData>
        extends Report<TData> {

    public AnonymizedReport(int id, Value value, TData data) {
        super(id, value, data);
    }

    public TData getReportData() {
        return data;
    }
}
