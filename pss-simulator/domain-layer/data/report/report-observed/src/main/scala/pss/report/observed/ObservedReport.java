package pss.report.observed;

import pss.data.valuemap.Value;
import pss.report.common.Report;

public abstract class ObservedReport<TData extends ObservedReportData> extends Report<TData> {

    public ObservedReport(int id, Value value, TData data) {
        super(id, value, data);
    }

    @Override
    public TData getReportData() {
        return data;
    }
}