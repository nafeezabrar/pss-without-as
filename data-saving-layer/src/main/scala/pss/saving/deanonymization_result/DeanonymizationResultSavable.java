package pss.saving.deanonymization_result;

import pss.result.deanonymization.DeanonymizationResult;

import java.util.Set;

public interface DeanonymizationResultSavable {
    void saveDeanonymizationResult(Set<DeanonymizationResult> anonymizationResults);
}
