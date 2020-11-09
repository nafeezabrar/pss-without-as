package pss.report.anonymizable;

import pss.data.anonymity.SingleAnonymity;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;

public class SingleIdgasAnonymizableReportData extends IdgasAnonymizableReportData<SingleLocalOoiCombination, SingleAnonymity> {
    public SingleIdgasAnonymizableReportData(SingleAnonymity anonymity, SingleLocalOoiCombination localOoiCombination) {
        super(anonymity, localOoiCombination);
    }
}
