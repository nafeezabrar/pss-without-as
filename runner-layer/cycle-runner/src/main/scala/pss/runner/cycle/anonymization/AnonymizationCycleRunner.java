package pss.runner.cycle.anonymization;

import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.runner.cycle.CycleRunnable;

public interface AnonymizationCycleRunner extends CycleRunnable<ObservedReport, AnonymizationResult> {}
