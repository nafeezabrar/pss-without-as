package pss.deanonymization;

import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.report.finalreport.SingleFinalReport;
import pss.result.deanonymization.SingleDeanonymizationResult;

public interface SingleDeanonymizable extends Deanonymizable<SingleFinalReport, SingleDeanonymizationResult, SingleLocalOoiCombination> {
}
