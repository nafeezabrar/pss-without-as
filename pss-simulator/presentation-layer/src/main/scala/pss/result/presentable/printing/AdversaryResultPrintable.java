package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.full_cycle.FullCycleResult;

import java.io.IOException;
import java.util.List;

public interface AdversaryResultPrintable<TAdversaryResult extends AdversaryResult> {
    void printAdversaryResult(TAdversaryResult adversaryResult, List<FullCycleResult> fullCycleResults, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, String resultDirectory) throws IOException, PssException, ClassNotFoundException;
}
