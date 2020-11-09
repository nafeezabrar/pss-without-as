package pss.runner.cycle.impl.fullcycle;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.runner.cycle.fullcycle.FullCycleRunner;
import pss.saving.full_cycle_result.FullCycleResultSavable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class FullCycleRunnerWithSaver extends FullCycleRunnerDecorator {
    protected final FullCycleResultSavable fullCycleResultSaver;

    public FullCycleRunnerWithSaver(FullCycleRunner fullCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, FullCycleResultSavable fullCycleResultSaver) {
        super(fullCycleRunner, pssType, ooiUserGroupMapper);
        this.fullCycleResultSaver = fullCycleResultSaver;
    }


    @Override
    public List<FullCycleResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException, URISyntaxException {
        List<FullCycleResult> fullCycleResults = fullCycleRunner.runCycle(observedReports);
        fullCycleResultSaver.saveFullCycleResult(fullCycleResults);
        return fullCycleResults;
    }
}
