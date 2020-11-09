package pss.adversary.report_filtering;

import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;

public class TargetUserLimitedTimeReportFilter implements ReportFilter<FinalReport> {
    protected final int startReportId;
    protected final int endReortId;
    protected final int targetUserId;

    public TargetUserLimitedTimeReportFilter(int startReportId, int endReortId, int targetUserId) {
        this.startReportId = startReportId;
        this.endReortId = endReortId;
        this.targetUserId = targetUserId;
    }

    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        List<FinalReport> leakedReports = new ArrayList<>();
        for (FinalReport finalReport : allReports) {
            int reportId = finalReport.getId();
            int reportedUserId = finalReport.getReportData().getUserId();
            if (reportId >= startReportId && reportId <= endReortId && reportedUserId == targetUserId)
                leakedReports.add(finalReport);
        }
        return leakedReports;
    }
}
