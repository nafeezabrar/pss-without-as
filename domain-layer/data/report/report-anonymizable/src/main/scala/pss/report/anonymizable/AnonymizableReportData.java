package pss.report.anonymizable;

import pss.data.anonymity.Anonymity;
import pss.report.common.ReportData;

public class AnonymizableReportData<TAnonymity extends Anonymity> extends ReportData {
    protected final TAnonymity anonymity;

    public AnonymizableReportData(TAnonymity anonymity) {
        this.anonymity = anonymity;
    }

    public TAnonymity getAnonymity() {
        return anonymity;
    }
}
