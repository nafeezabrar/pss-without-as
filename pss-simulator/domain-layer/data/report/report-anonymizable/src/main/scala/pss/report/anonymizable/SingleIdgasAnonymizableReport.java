package pss.report.anonymizable;

import pss.data.anonymity.SingleAnonymity;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;

public class SingleIdgasAnonymizableReport extends AnonymizableReport<SingleIdgasAnonymizableReportData> {

    public SingleIdgasAnonymizableReport(int id, Value value, SingleIdgasAnonymizableReportData data) {
        super(id, value, data);
    }

    public SingleLocalOoiCombination getLocalOoiCombination() {
        return data.getLocalOoiCombination();
    }

    public SingleAnonymity getAnonymity() {
        return data.getAnonymity();
    }
}
