package pss.report.anonymizable;

import pss.data.anonymity.Anonymity;

public abstract class RasAnonymizableReportData<TAnonymity extends Anonymity> extends AnonymizableReportData<TAnonymity> {
    public RasAnonymizableReportData(TAnonymity anonymity) {
        super(anonymity);
    }
}
