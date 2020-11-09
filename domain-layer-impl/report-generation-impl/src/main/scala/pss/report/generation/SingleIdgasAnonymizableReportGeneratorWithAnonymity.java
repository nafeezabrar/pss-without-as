package pss.report.generation;

import pss.data.anonymity.SingleAnonymity;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.report.anonymizable.SingleIdgasAnonymizableReport;
import pss.report.anonymizable.SingleIdgasAnonymizableReportData;
import pss.report.observed.SingleObservedReport;
import pss.report.observed.SingleObservedReportData;

public class SingleIdgasAnonymizableReportGeneratorWithAnonymity implements AnonymizableReportGenerable<SingleObservedReport, SingleAnonymity, SingleIdgasAnonymizableReport> {

    @Override
    public SingleIdgasAnonymizableReport generateAnonymizableReport(SingleObservedReport observedReport, SingleAnonymity anonymity) {
        int reportId = observedReport.getId();
        Value reportedValue = observedReport.getValue();
        SingleObservedReportData reportData = observedReport.getReportData();
        SingleLocalOoiCombination localOoiCombination = reportData.getLocalOoiCombination();
        SingleIdgasAnonymizableReportData anonymizableReportData = new SingleIdgasAnonymizableReportData(anonymity, localOoiCombination);
        return new SingleIdgasAnonymizableReport(reportId, reportedValue, anonymizableReportData);
    }
}
