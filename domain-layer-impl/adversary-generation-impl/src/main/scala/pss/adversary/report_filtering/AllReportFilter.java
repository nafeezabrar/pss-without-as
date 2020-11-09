package pss.adversary.report_filtering;


import pss.report.finalreport.FinalReport;

import java.util.List;

public class AllReportFilter implements ReportFilter<FinalReport> {

    @Override
    public List<FinalReport> getLeakedReports(List<FinalReport> allReports) {
        return allReports;
    }
}
