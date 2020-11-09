package pss.conversion.data_to_result;

import pss.data.pss_type.PssType;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.PresentableResult;

import java.util.List;

public interface FullCycleResultWithIntermediateDecodabilityToResultConvertable {
    PresentableResult generateFullCycleWithDecodabilityPresentableResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, IntermediatePointDecodabilityResults decodabilityResultsMap) throws PssException;
}
