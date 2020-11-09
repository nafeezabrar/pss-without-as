package pss.adversary.report_filtering;

import pss.library.MyRandom;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;

public class LimitedTimeWithPercentageReportFilter implements ReportFilter<FinalReport> {
    protected final MyRandom myRandom;
    protected final double percentage;

    public LimitedTimeWithPercentageReportFilter(MyRandom myRandom, double percentage) {
        this.myRandom = myRandom;
        this.percentage = percentage;
    }

    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        int startReportId = -1, endReortId;
        int totalReports = allReports.size();
        int totalLeakReports = (int) ((totalReports * percentage) / 100.0);
        while (true) {
            int randomId = myRandom.nextInt(totalReports);
            if (randomId + totalLeakReports < totalReports) {
                startReportId = randomId;
                endReortId = startReportId + totalLeakReports - 1;
                break;
            }
        }
        List<FinalReport> leakedReports = new ArrayList<>();
        for (FinalReport finalReport : allReports) {
            int reportId = finalReport.getId();
            if (reportId >= startReportId && reportId <= endReortId)
                leakedReports.add(finalReport);
        }
        return leakedReports;
    }
}
