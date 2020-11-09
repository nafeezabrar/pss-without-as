package pss.report.anonymizable;

import pss.data.anonymity.MultiAnonymity;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.valuemap.Value;

public class MultiIdgasAnonymizableReport extends AnonymizableReport<IdgasAnonymizableReportData<MultiLocalOoiCombination, MultiAnonymity>> {

    public MultiIdgasAnonymizableReport(int id, Value value, IdgasAnonymizableReportData<MultiLocalOoiCombination, MultiAnonymity> data) {
        super(id, value, data);
    }

    public MultiLocalOoiCombination getLocalOoiCombination() {
        return data.getLocalOoiCombination();
    }

    public MultiAnonymity getAnonymity() {
        return data.getAnonymity();
    }
}
