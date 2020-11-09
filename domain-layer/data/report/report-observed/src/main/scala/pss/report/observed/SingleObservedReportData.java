package pss.report.observed;

import pss.data.ooi.local.combination.SingleLocalOoiCombination;

public class SingleObservedReportData extends ObservedReportData<SingleLocalOoiCombination> {

    public SingleObservedReportData(int userId, SingleLocalOoiCombination localOoiCombination) {
        super(userId, localOoiCombination);
    }
}
