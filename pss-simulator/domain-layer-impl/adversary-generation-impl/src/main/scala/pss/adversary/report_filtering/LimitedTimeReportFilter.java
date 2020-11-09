package pss.adversary.report_filtering;

import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;

public class LimitedTimeReportFilter implements ReportFilter<FinalReport> {
    protected final int startReportId;
    protected final int endReortId;

    public LimitedTimeReportFilter(int startReportId, int endReortId) {
        this.startReportId = startReportId;
        this.endReortId = endReortId;
    }

    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        List<FinalReport> leakedReports = new ArrayList<>();
        for (FinalReport finalReport : allReports) {
            int reportId = finalReport.getId();
            if (reportId >= startReportId && reportId <= endReortId)
                leakedReports.add(finalReport);
        }
        return leakedReports;
    }
}
