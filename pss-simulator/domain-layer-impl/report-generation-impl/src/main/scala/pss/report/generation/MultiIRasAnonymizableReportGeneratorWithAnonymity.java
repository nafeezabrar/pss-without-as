package pss.report.generation;

import pss.data.anonymity.MultiRasAnonymity;
import pss.data.valuemap.Value;
import pss.report.anonymizable.MultiRasAnonymizableReport;
import pss.report.anonymizable.MultiRasAnonymizableReportData;
import pss.report.observed.MultiObservedReport;

public class MultiIRasAnonymizableReportGeneratorWithAnonymity implements AnonymizableReportGenerable<MultiObservedReport, MultiRasAnonymity, MultiRasAnonymizableReport> {

    @Override
    public MultiRasAnonymizableReport generateAnonymizableReport(MultiObservedReport observedReport, MultiRasAnonymity anonymity) {
        int reportId = observedReport.getId();
        Value reportedValue = observedReport.getValue();
        MultiRasAnonymizableReportData reportData = new MultiRasAnonymizableReportData(anonymity);
        return new MultiRasAnonymizableReport(reportId, reportedValue, reportData);
    }
}
