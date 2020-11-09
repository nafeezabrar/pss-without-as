package pss.deanonymization;

import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.valuemap.Value;
import pss.report.finalreport.FinalReport;
import pss.result.deanonymization.DeanonymizationResult;

import java.util.Map;

public interface Deanonymizable<TFinalReport extends FinalReport, TDeanonymizationResult extends DeanonymizationResult, TLocalOoiCombination extends LocalOoiCombination> {
    TDeanonymizationResult deanonymize(TFinalReport finalReport);

    Map<TLocalOoiCombination, Value> getDecodedValueMap();
}
