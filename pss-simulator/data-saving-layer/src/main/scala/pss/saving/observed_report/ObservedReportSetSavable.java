package pss.saving.observed_report;

import pss.data.pss_type.PssType;
import pss.report.observed.ObservedReport;

import java.util.List;

public interface ObservedReportSetSavable {
    void saveObservedReports(List<ObservedReport> observedReportList, PssType pssType);
}
