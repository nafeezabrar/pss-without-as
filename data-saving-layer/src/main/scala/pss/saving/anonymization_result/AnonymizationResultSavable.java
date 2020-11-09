package pss.saving.anonymization_result;

import pss.result.anonymization.AnonymizationResult;

import java.util.List;

public interface AnonymizationResultSavable {
    void saveAnonymizationResult(List<AnonymizationResult> anonymizationResults);
}
