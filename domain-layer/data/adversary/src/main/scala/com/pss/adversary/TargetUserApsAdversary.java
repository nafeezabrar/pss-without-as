package com.pss.adversary;

import pss.deanonymization.Deanonymizable;
import pss.report.finalreport.FinalReport;

import java.util.List;

public class TargetUserApsAdversary extends ApsAdversary {
    protected final int targetUserId;
    protected final List<FinalReport> finalReportsByTargetUser;
    public TargetUserApsAdversary(Deanonymizable deanonymizable, ApsAdversaryType apsAdversaryType, List<FinalReport> leakedReports, List<FinalReport> allFinalReports, ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod, int targetUserId, List<FinalReport> finalReportsByTargetUser) {
        super(deanonymizable, apsAdversaryType, leakedReports, allFinalReports, apsAdversaryReportFilteringMethod);
        this.targetUserId = targetUserId;
        this.finalReportsByTargetUser = finalReportsByTargetUser;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public List<FinalReport> getFinalReportsByTargetUser() {
        return finalReportsByTargetUser;
    }
}
