package pss.post.result.analyzation.fullsimulation;

import com.pss.adversary.Adversary;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class EmptyAnalyzerWithAdversary implements FullSimulationWithAdversaryPostResultAnalyzer {

    @Override
    public void analyzeResult(Adversary adversary, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, AdversaryResult adversaryResult, PssVariables pssVariables) throws PssException, IOException, URISyntaxException {
        // do nothing
    }
}
