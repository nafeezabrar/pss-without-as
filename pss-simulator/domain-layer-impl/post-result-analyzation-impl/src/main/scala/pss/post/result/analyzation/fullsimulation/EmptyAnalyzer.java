package pss.post.result.analyzation.fullsimulation;

import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public class EmptyAnalyzer implements FullSimulationPostResultAnalyzer {

    @Override
    public void analyzeResult(PssVariables pssVariables, Set<PssGroup> pssGroups, Set<User> users, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults) throws PssException, IOException, URISyntaxException {
        // do nothing
    }
}
