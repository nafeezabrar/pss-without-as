package pss.conversion.data_to_result;

import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

public interface AdversaryInterceptedReportToResultConvertable<TPresentableResult extends PresentableResult> {
    TPresentableResult generateAdversaryReportCountResult(int totalReportCount) throws PssException;
}
