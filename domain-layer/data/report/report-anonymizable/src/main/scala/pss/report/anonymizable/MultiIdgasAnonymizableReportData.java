package pss.report.anonymizable;

import pss.data.anonymity.MultiAnonymity;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;

public class MultiIdgasAnonymizableReportData extends IdgasAnonymizableReportData<MultiLocalOoiCombination, MultiAnonymity> {
    public MultiIdgasAnonymizableReportData(MultiAnonymity anonymity, MultiLocalOoiCombination localOoiCombination) {
        super(anonymity, localOoiCombination);
    }
}
