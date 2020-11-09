package pss.adversary.report_filtering;

import pss.report.finalreport.FinalReport;
import pss.report.finalreport.FinalReportData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TargetUserSetReportFilter implements ReportFilter<FinalReport> {
    protected final Set<Integer> userIdSet;

    public TargetUserSetReportFilter(Set<Integer> userIdSet) {
        this.userIdSet = userIdSet;
    }


    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        List<FinalReport> leakedReports = new ArrayList<>();

        for (FinalReport finalReport : allReports) {
            int userId = getUserId(finalReport);
            if (userIdSet.contains(userId))
                leakedReports.add(finalReport);
        }
        return leakedReports;
    }

    private int getUserId(FinalReport finalReport) {
        FinalReportData reportData = finalReport.getReportData();
        return reportData.getUserId();
    }
}
