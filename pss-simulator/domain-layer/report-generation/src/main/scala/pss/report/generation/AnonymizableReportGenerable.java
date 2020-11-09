package pss.report.generation;

import pss.data.anonymity.Anonymity;
import pss.report.anonymizable.AnonymizableReport;
import pss.report.observed.ObservedReport;

public interface AnonymizableReportGenerable<TObservedReport extends ObservedReport, TAnonymity extends Anonymity, TAnonymizableReport extends AnonymizableReport> {
    TAnonymizableReport generateAnonymizableReport(TObservedReport observedReport, TAnonymity anonymity);
}
