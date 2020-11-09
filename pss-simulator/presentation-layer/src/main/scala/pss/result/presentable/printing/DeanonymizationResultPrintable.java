package pss.result.presentable.printing;

import pss.result.deanonymization.DeanonymizationResult;

import java.util.List;

public interface DeanonymizationResultPrintable {
    void printDeanonymizationResult(List<DeanonymizationResult> deanonymizationResults);
}
