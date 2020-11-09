package pss.conversion.data_to_result;

import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

public interface AdversaryOwnReportToResultConvertable<TPresentableResult extends PresentableResult> {
    TPresentableResult generateObservedReportResult(int totalReportCount) throws PssException;
}
