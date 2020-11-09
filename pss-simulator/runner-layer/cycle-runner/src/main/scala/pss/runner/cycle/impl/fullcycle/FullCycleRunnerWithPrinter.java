package pss.runner.cycle.impl.fullcycle;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.FullCycleResultPrintable;
import pss.runner.cycle.fullcycle.FullCycleRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class FullCycleRunnerWithPrinter extends FullCycleRunnerDecorator {

    protected final FullCycleResultPrintable fullCycleResultPrintable;

    public FullCycleRunnerWithPrinter(FullCycleRunner fullCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, FullCycleResultPrintable fullCycleResultPrintable) {
        super(fullCycleRunner, pssType, ooiUserGroupMapper);
        this.fullCycleResultPrintable = fullCycleResultPrintable;
    }


    @Override
    public List<FullCycleResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException, URISyntaxException {
        List<FullCycleResult> fullCycleResults = fullCycleRunner.runCycle(observedReports);
        fullCycleResultPrintable.printFullCycleResult(fullCycleResults, pssType, ooiUserGroupMapper, observedReports);
        return fullCycleResults;
    }
}
