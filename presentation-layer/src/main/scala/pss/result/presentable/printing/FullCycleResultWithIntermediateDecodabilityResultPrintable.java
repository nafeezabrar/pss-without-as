package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;

import java.io.IOException;
import java.util.List;

public interface FullCycleResultWithIntermediateDecodabilityResultPrintable {
    void printFullCycleResultWithIntermediateDecodability(List<FullCycleResult> fullCycleResults, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, IntermediatePointDecodabilityResults decodabilityResultsMap) throws IOException, PssException;
}
