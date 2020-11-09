package pss.report.anonymizable;

import pss.data.anonymity.SingleAasAnonymity;
import pss.data.valuemap.Value;

public class SingleAasAnonymizableReport extends AnonymizableReport<SingleAasAnonymizableReportData> {

    public SingleAasAnonymizableReport(int id, Value value, SingleAasAnonymizableReportData data) {
        super(id, value, data);
    }

    public SingleAasAnonymity getAnonymity() {
        return data.getAnonymity();
    }
}
