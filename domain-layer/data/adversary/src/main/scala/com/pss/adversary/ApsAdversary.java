package com.pss.adversary;

import pss.deanonymization.Deanonymizable;
import pss.report.finalreport.FinalReport;

import java.util.List;
import java.util.Set;

public class ApsAdversary extends Adversary {
    protected final ApsAdversaryType apsAdversaryType;
    protected final List<FinalReport> leakedReports;
    protected final List<FinalReport> allFinalReports;
    protected final ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod;


    public ApsAdversary(Deanonymizable deanonymizable, ApsAdversaryType apsAdversaryType, List<FinalReport> leakedReports, List<FinalReport> allFinalReports, ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod) {
        super(AdversaryType.APS_ADVERSARY, deanonymizable);
        this.apsAdversaryType = apsAdversaryType;
        this.leakedReports = leakedReports;
        this.allFinalReports = allFinalReports;
        this.apsAdversaryReportFilteringMethod = apsAdversaryReportFilteringMethod;
    }

    public ApsAdversaryType getApsAdversaryType() {
        return apsAdversaryType;
    }

    public List<FinalReport> getLeakedReports() {
        return leakedReports;
    }

    public List<FinalReport> getAllFinalReports() {
        return allFinalReports;
    }

    @Override
    public Set<Integer> getAdversaryUserIds() {
        return apsAdversaryType.getAdversaryUserIds();
    }

    public ApsAdversaryReportFilteringMethod getApsAdversaryReportFilteringMethod() {
        return apsAdversaryReportFilteringMethod;
    }

    public enum ApsAdversaryReportFilteringMethod {
        ALL_REPORTS,
        RANDOM_REPORTS,
        RANDOM_REPORTS_PERCENTAGE,
        LIMITED_TIME_ALL_REPORTS,
        LIMITED_TIME_PERCENTAGE,
        LIMITED_TIME_RANDOM_REPORTS,
        TARGET_USER_REPORT,
        TARGET_USER_SET_REPORT,
        TARGET_USER_LIMITED_TIME
    }
}
