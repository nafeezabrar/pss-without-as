package pss.report.anonymizable;

import pss.data.anonymity.SingleRasAnonymity;
import pss.data.valuemap.Value;

public class SingleRasAnonymizableReport extends AnonymizableReport<SingleRasAnonymizableReportData> {

    public SingleRasAnonymizableReport(int id, Value value, SingleRasAnonymizableReportData data) {
        super(id, value, data);
    }

    public SingleRasAnonymity getAnonymity() {
        return data.getAnonymity();
    }
}
