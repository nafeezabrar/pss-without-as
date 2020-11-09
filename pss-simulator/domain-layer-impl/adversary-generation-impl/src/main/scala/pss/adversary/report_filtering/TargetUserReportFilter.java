package pss.adversary.report_filtering;

import pss.report.finalreport.FinalReport;
import pss.report.finalreport.FinalReportData;

import java.util.ArrayList;
import java.util.List;

public class TargetUserReportFilter implements ReportFilter<FinalReport> {
    protected final int targetUserId;

    public TargetUserReportFilter(int targetUserId) {
        this.targetUserId = targetUserId;
    }


    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        List<FinalReport> leakedReports = new ArrayList<>();
        for (FinalReport finalReport : allReports) {
            int userId = getUserId(finalReport);
            if (targetUserId == userId)
                leakedReports.add(finalReport);
        }
        return leakedReports;
    }

    private int getUserId(FinalReport finalReport) {
        FinalReportData reportData = finalReport.getReportData();
        return reportData.getUserId();
    }
}
