package pss.report.anonymizable;

import pss.data.valuemap.Value;
import pss.report.common.Report;

public abstract class AnonymizableReport<TData extends AnonymizableReportData> extends Report<TData> {

    public AnonymizableReport(int id, Value value, TData data) {
        super(id, value, data);
    }

    @Override
    public TData getReportData() {
        return data;
    }
}
