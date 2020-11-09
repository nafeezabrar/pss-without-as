package pss.deanonymization;

import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.report.finalreport.MultiFinalReport;
import pss.result.deanonymization.MultiDeanonymizationResult;

public interface MultiDeanonymizable extends Deanonymizable<MultiFinalReport, MultiDeanonymizationResult, MultiLocalOoiCombination> {
}
