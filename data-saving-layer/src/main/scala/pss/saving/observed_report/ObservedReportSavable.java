package pss.saving.observed_report;

import pss.data.pss_type.PssType;
import pss.report.observed.ObservedReport;

public interface ObservedReportSavable {
    void saveObservedReports(ObservedReport observedReport, PssType pssType);
}
