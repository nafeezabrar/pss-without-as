package pss.conversion.data_to_result;

import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

import java.util.List;

public interface DecodabilityResultToResultConvertable<TPresentableResult extends PresentableResult> {
    TPresentableResult generateReportCountResult(List<Double> decodabilities, int iteration) throws PssException;
}
