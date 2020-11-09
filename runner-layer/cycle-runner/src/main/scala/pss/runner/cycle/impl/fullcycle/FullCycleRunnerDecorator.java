package pss.runner.cycle.impl.fullcycle;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.runner.cycle.fullcycle.FullCycleRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FullCycleRunnerDecorator implements FullCycleRunner {
    protected final FullCycleRunner fullCycleRunner;
    protected final PssType pssType;
    protected final OoiUserGroupMappable ooiUserGroupMapper;

    public FullCycleRunnerDecorator(FullCycleRunner fullCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper) {
        this.fullCycleRunner = fullCycleRunner;
        this.pssType = pssType;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
    }

    @Override
    public List<FullCycleResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException, URISyntaxException {
        List<FullCycleResult> fullCycleResults = new ArrayList<>();
        for (ObservedReport observedReport : observedReports) {
            FullCycleResult fullCycleResult = fullCycleRunner.runCycle(observedReport);
            fullCycleResults.add(fullCycleResult);
        }
        return fullCycleResults;
    }

    @Override
    public FullCycleResult runCycle(ObservedReport observedReport) throws PssException {
        return fullCycleRunner.runCycle(observedReport);
    }
}
