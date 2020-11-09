package pss.conversion.data_to_result;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.PresentableResult;

import java.io.IOException;
import java.util.List;

public interface SimplestAdversaryResultToResultConvertable {
    PresentableResult generateAdversaryResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryRunResultWithSingleOcTable, String resultDirectory) throws PssException, IOException, ClassNotFoundException;
}
