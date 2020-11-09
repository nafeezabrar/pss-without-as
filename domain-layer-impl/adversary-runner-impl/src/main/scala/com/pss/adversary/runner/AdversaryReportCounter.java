package com.pss.adversary.runner;

import com.pss.adversary.ApsAdversary;
import com.pss.adversary.ApsAdversaryType;
import com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import com.pss.adversary.MultiUserApsAdversaryType;
import com.pss.adversary.SingleUserApsAdversaryType;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.report.finalreport.FinalReport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdversaryReportCounter {
    public static int getOwnReportsByAdversary(ApsAdversary adversary) throws PssException {
        ApsAdversaryType apsAdversaryType = adversary.getApsAdversaryType();
        ApsAdversaryUserStrength apsAdversaryUserStrength = apsAdversaryType.getApsAdversaryUserStrength();
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                return getSingleUserReportedByAdversary((SingleUserApsAdversaryType) apsAdversaryType, adversary.getAllFinalReports());
            case DISGUISED_AS_MULTI_USER:
                return getMultiUserReportedByAdversary((MultiUserApsAdversaryType) apsAdversaryType, adversary.getAllFinalReports());
        }
        throw new PssException(String.format("adversary strength %s not matched", apsAdversaryUserStrength.toString()));
    }

    public static int getOwnReportedValueByAdversary(ApsAdversary adversary) throws PssException {
        ApsAdversaryType apsAdversaryType = adversary.getApsAdversaryType();
        ApsAdversaryUserStrength apsAdversaryUserStrength = apsAdversaryType.getApsAdversaryUserStrength();
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                return getSingleUserReportedValueByAdversary((SingleUserApsAdversaryType) apsAdversaryType, adversary.getAllFinalReports());
            case DISGUISED_AS_MULTI_USER:
                return getMultiUserReportedValueByAdversary((MultiUserApsAdversaryType) apsAdversaryType, adversary.getAllFinalReports());
        }
        throw new PssException(String.format("adversary strength %s not matched", apsAdversaryUserStrength.toString()));
    }

    private static int getMultiUserReportedValueByAdversary(MultiUserApsAdversaryType apsAdversaryType, List<FinalReport> allFinalReports) {
        Set<Value> reportedValues = new HashSet<>();
        Set<Integer> adversaryUserIds = apsAdversaryType.getUserIds();
        for (FinalReport finalReport : allFinalReports) {
            int userId = finalReport.getReportData().getUserId();
            if (adversaryUserIds.contains(userId))
                reportedValues.add(finalReport.getValue());
        }
        return reportedValues.size();
    }

    private static int getSingleUserReportedValueByAdversary(SingleUserApsAdversaryType apsAdversaryType, List<FinalReport> allFinalReports) {
        Set<Value> reportedValues = new HashSet<>();
        int adversaryUserId = apsAdversaryType.getUserId();
        for (FinalReport finalReport : allFinalReports) {
            int userId = finalReport.getReportData().getUserId();
            if (adversaryUserId == userId) {
                reportedValues.add(finalReport.getValue());
            }
        }
        return reportedValues.size();
    }

    private static int getSingleUserReportedByAdversary(SingleUserApsAdversaryType apsAdversaryType, List<FinalReport> allReports) {
        int totalReport = 0;
        int adversaryUserId = apsAdversaryType.getUserId();
        for (FinalReport finalReport : allReports) {
            int userId = finalReport.getReportData().getUserId();
            if (adversaryUserId == userId)
                totalReport++;
        }
        return totalReport;
    }

    private static int getMultiUserReportedByAdversary(MultiUserApsAdversaryType adversaryType, List<FinalReport> allReports) {
        int totalReport = 0;
        Set<Integer> adversaryUserIds = adversaryType.getUserIds();
        for (FinalReport finalReport : allReports) {
            int userId = finalReport.getReportData().getUserId();
            if (adversaryUserIds.contains(userId))
                totalReport++;
        }
        return totalReport;
    }
}
