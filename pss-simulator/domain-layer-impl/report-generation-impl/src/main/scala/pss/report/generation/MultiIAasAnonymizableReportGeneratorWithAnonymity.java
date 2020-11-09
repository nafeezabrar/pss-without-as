package pss.report.generation;

import pss.data.anonymity.MultiAasAnonymity;
import pss.data.valuemap.Value;
import pss.report.anonymizable.MultiAasAnonymizableReport;
import pss.report.anonymizable.MultiAasAnonymizableReportData;
import pss.report.observed.MultiObservedReport;

public class MultiIAasAnonymizableReportGeneratorWithAnonymity implements AnonymizableReportGenerable<MultiObservedReport, MultiAasAnonymity, MultiAasAnonymizableReport> {

    @Override
    public MultiAasAnonymizableReport generateAnonymizableReport(MultiObservedReport observedReport, MultiAasAnonymity anonymity) {
        int reportId = observedReport.getId();
        Value reportedValue = observedReport.getValue();
        MultiAasAnonymizableReportData reportData = new MultiAasAnonymizableReportData(anonymity);
        return new MultiAasAnonymizableReport(reportId, reportedValue, reportData);
    }
}
