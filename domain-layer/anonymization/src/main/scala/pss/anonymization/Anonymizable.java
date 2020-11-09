package pss.anonymization;

import pss.report.finalreport.FinalReport;
import pss.result.anonymization.AnonymizationResult;

public interface Anonymizable<TAnonymizableReport extends pss.report.common.Report, TAnonymizationResult extends AnonymizationResult, TFinalReport extends FinalReport> {
    TAnonymizationResult anonymize(TAnonymizableReport anonymizableReport);

    void updateIfRequired(TFinalReport finalReport);
}
