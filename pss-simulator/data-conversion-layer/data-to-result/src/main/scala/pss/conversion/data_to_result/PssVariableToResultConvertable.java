package pss.conversion.data_to_result;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

public interface PssVariableToResultConvertable<TPresentableResult extends PresentableResult> {
    TPresentableResult generateResult(PssVariables pssVariables, PssType pssType) throws PssException;
}
