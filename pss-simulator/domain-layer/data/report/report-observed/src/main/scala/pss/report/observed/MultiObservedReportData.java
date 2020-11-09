package pss.report.observed;

import pss.data.ooi.local.combination.MultiLocalOoiCombination;

public class MultiObservedReportData extends ObservedReportData<MultiLocalOoiCombination> {

    public MultiObservedReportData(int userId, MultiLocalOoiCombination ooiIdCombination) {
        super(userId, ooiIdCombination);
    }
}
