package pss.adversary.report_filtering;

import pss.library.MyRandom;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;

public class LimitedTimeRandomReportFilter implements ReportFilter<FinalReport> {
    protected final int startReportId;
    protected final int endReortId;
    protected final MyRandom myRandom;
    protected final int totalReport;

    public LimitedTimeRandomReportFilter(int startReportId, int endReortId, MyRandom myRandom, int totalReport) {
        this.startReportId = startReportId;
        this.endReortId = endReortId;
        this.myRandom = myRandom;
        this.totalReport = totalReport;
    }

    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        List<FinalReport> leakedReports = new ArrayList<>();
        List<FinalReport> reportsWithinTime = getReportsWithinTime(allReports);
        return getRandomReports(reportsWithinTime);
    }

    private List<FinalReport> getReportsWithinTime(List<FinalReport> allReports) {
        List<FinalReport> reportsWithinTime = new ArrayList<>();
        for (FinalReport finalReport : allReports) {
            int reportId = finalReport.getId();
            if (reportId >= startReportId && reportId <= endReortId)
                reportsWithinTime.add(finalReport);
        }
        return reportsWithinTime;
    }

    private List<FinalReport> getRandomReports(List<FinalReport> reportsWithinTime) {
        int total = 0;
        int size = reportsWithinTime.size();
        List<FinalReport> leakedReports = new ArrayList<>();
        List<Integer> leakedReportIds = new ArrayList<>();
        while (total != totalReport) {

            int index = myRandom.nextInt(size);
            FinalReport finalReport = reportsWithinTime.get(index);
            if (!leakedReportIds.contains(finalReport.getId())) {
                leakedReportIds.add(finalReport.getId());
                leakedReports.add(finalReport);
                total++;
            }
        }
        leakedReports.sort((r1, r2) -> Integer.compare(r1.getId(), r2.getId()));
        return leakedReports;
    }
}
