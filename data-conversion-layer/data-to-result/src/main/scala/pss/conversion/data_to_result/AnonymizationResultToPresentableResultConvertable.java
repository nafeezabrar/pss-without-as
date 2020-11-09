package pss.conversion.data_to_result;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.result.presentable.PresentableResult;

import java.util.List;

public interface AnonymizationResultToPresentableResultConvertable {
    PresentableResult generateAnonymizationResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<AnonymizationResult> anonymizatinoResult, List<ObservedReport> observedReports) throws PssException;
}
