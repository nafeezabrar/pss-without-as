package pss.report.anonymizable;

import pss.data.anonymity.Anonymity;

public abstract class AasAnonymizableReportData<TAnonymity extends Anonymity> extends AnonymizableReportData<TAnonymity> {
    public AasAnonymizableReportData(TAnonymity anonymity) {
        super(anonymity);
    }
}
