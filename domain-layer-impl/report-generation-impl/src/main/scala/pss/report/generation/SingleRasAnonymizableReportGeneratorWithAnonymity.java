package pss.report.generation;

import pss.data.anonymity.SingleRasAnonymity;
import pss.data.valuemap.Value;
import pss.report.anonymizable.SingleRasAnonymizableReport;
import pss.report.anonymizable.SingleRasAnonymizableReportData;
import pss.report.observed.SingleObservedReport;

public class SingleRasAnonymizableReportGeneratorWithAnonymity implements AnonymizableReportGenerable<SingleObservedReport, SingleRasAnonymity, SingleRasAnonymizableReport> {
    @Override
    public SingleRasAnonymizableReport generateAnonymizableReport(SingleObservedReport observedReport, SingleRasAnonymity anonymity) {
        int reportId = observedReport.getId();
        Value reportedValue = observedReport.getValue();
        SingleRasAnonymizableReportData reportData = new SingleRasAnonymizableReportData(anonymity);
        return new SingleRasAnonymizableReport(reportId, reportedValue, reportData);
    }
}
