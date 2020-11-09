package pss.conversion.data_to_result;

import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.presentable.PresentableResult;

import java.util.List;

public interface ObservedReportToResultConvertable {
    PresentableResult generateObservedReportResult(List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws PssException;
}
