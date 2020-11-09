package pss.conversion.data_to_result;

import pss.data.user.User;
import pss.exception.PssException;
import pss.result.presentable.PresentableResult;

import java.util.Map;

public interface ObservedReportCountToResultConvertable<TPresentableResult extends PresentableResult> {
    TPresentableResult generateReportCountResult(Map<User, Integer> reportCountMap) throws PssException;
}
