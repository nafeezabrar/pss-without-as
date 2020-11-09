package pss.conversion.data_to_result;

import pss.result.deanonymization.DeanonymizationResult;
import pss.result.presentable.PresentableResult;

public interface DeanonymizationToResultConvertable {
    PresentableResult generateDeanonymizationResult(DeanonymizationResult deanonymizationResult);
}
