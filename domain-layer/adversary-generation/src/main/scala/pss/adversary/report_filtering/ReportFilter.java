package pss.adversary.report_filtering;

import pss.report.common.Report;

import java.util.List;

public interface ReportFilter<TReport extends Report> {
    List<TReport> getLeakedReports(List<TReport> allReports);
}
