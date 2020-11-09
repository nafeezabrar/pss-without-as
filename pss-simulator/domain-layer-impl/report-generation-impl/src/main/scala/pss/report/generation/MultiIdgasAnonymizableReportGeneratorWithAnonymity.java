package pss.report.generation;

import pss.data.anonymity.MultiAnonymity;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.report.anonymizable.MultiIdgasAnonymizableReport;
import pss.report.anonymizable.MultiIdgasAnonymizableReportData;
import pss.report.observed.MultiObservedReport;
import pss.report.observed.MultiObservedReportData;

public class MultiIdgasAnonymizableReportGeneratorWithAnonymity implements AnonymizableReportGenerable<MultiObservedReport, MultiAnonymity, MultiIdgasAnonymizableReport> {

    @Override
    public MultiIdgasAnonymizableReport generateAnonymizableReport(MultiObservedReport observedReport, MultiAnonymity anonymity) {
        int reportId = observedReport.getId();
        Value reportedValue = observedReport.getValue();
        MultiObservedReportData reportData = observedReport.getReportData();
        MultiLocalOoiCombination localOoiCombination = reportData.getLocalOoiCombination();
        MultiIdgasAnonymizableReportData anonymizableReportData = new MultiIdgasAnonymizableReportData(anonymity, localOoiCombination);
        return new MultiIdgasAnonymizableReport(reportId, reportedValue, anonymizableReportData);
    }
}
