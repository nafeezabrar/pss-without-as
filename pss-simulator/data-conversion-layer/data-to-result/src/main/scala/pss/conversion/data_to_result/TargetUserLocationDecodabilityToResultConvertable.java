package pss.conversion.data_to_result;

import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

public interface TargetUserLocationDecodabilityToResultConvertable<TPresentableResult extends PresentableResult> {
    TPresentableResult generateTargetUserLocationDecodabilityResult(double locationDecodability) throws PssException;
}
