package pss.report.generation;

import pss.data.anonymity.SingleAasAnonymity;
import pss.data.valuemap.Value;
import pss.report.anonymizable.SingleAasAnonymizableReport;
import pss.report.anonymizable.SingleAasAnonymizableReportData;
import pss.report.observed.SingleObservedReport;

public class SingleAasAnonymizableReportGeneratorWithAnonymity implements AnonymizableReportGenerable<SingleObservedReport, SingleAasAnonymity, SingleAasAnonymizableReport> {

    @Override
    public SingleAasAnonymizableReport generateAnonymizableReport(SingleObservedReport observedReport, SingleAasAnonymity anonymity) {
        int reportId = observedReport.getId();
        Value reportedValue = observedReport.getValue();
        SingleAasAnonymizableReportData reportData = new SingleAasAnonymizableReportData(anonymity);
        return new SingleAasAnonymizableReport(reportId, reportedValue, reportData);
    }
}
