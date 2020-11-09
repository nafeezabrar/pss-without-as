package pss.adversary.report_filtering;

import pss.library.MyRandom;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;

public class RandomReportFilter implements ReportFilter<FinalReport> {
    protected final MyRandom myRandom;
    protected final int totalReport;

    public RandomReportFilter(MyRandom myRandom, int totalReport) {
        this.myRandom = myRandom;
        this.totalReport = totalReport;
    }

    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        List<FinalReport> leakedReports = new ArrayList<>();
        List<Integer> leakedReportIds = new ArrayList<>();
        int total = 0;
        int size = allReports.size();
        while (total != totalReport) {

            int index = myRandom.nextInt(size);
            FinalReport finalReport = allReports.get(index);
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
