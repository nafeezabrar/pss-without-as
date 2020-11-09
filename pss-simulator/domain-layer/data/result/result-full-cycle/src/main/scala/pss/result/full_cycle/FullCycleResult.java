package pss.result.full_cycle;

import pss.report.finalreport.FinalReport;
import pss.result.Result;
import pss.result.anonymization.AnonymizationResult;
import pss.result.deanonymization.DeanonymizationResult;

public class FullCycleResult extends Result {
    protected final AnonymizationResult anonymizationResult;
    protected final DeanonymizationResult deanonymizationResult;
    protected final FinalReport finalReport;
    protected final int totalDecoded;

    public FullCycleResult(AnonymizationResult anonymizationResult, DeanonymizationResult deanonymizationResult, FinalReport finalReport, int totalDecoded) {
        this.anonymizationResult = anonymizationResult;
        this.deanonymizationResult = deanonymizationResult;
        this.finalReport = finalReport;
        this.totalDecoded = totalDecoded;
    }

    public AnonymizationResult getAnonymizationResult() {
        return anonymizationResult;
    }

    public DeanonymizationResult getDeanonymizationResult() {
        return deanonymizationResult;
    }

    public FinalReport getFinalReport() {
        return finalReport;
    }

    public int getTotalDecoded() {
        return totalDecoded;
    }
}
