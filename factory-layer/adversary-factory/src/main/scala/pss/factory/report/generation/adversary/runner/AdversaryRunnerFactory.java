package pss.factory.report.generation.adversary.runner;

import com.pss.adversary.Adversary;
import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;

import java.util.List;
import java.util.Set;

public interface AdversaryRunnerFactory<TAdversary extends Adversary> {
    AdversaryRunnable createAdversaryRunner(TAdversary adversary, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, Set<PssGroup> pssGroups, List<FinalReport> finalReports) throws PssException;
}
