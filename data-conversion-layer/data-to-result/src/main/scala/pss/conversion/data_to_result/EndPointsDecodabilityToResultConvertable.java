package pss.conversion.data_to_result;

import pss.decodability.DecodabilityResultType;
import pss.decodability.EndPointDecodabilityResults;
import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

public interface EndPointsDecodabilityToResultConvertable<TPresentableResult extends PresentableResult> {
    TPresentableResult generateResult(EndPointDecodabilityResults endPointDecodabilityResults, DecodabilityResultType decodabilityResultType) throws PssException;
}
