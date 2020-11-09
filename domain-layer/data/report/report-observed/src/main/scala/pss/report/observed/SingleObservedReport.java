package pss.report.observed;

import pss.data.valuemap.Value;

public class SingleObservedReport extends ObservedReport<SingleObservedReportData> {

    public SingleObservedReport(int id, Value value, SingleObservedReportData data) {
        super(id, value, data);
    }
}
