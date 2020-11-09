package pss.runner.cycle.impl.anonymization;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.runner.cycle.anonymization.AnonymizationCycleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AnonymizationCycleRunnerDecorator implements AnonymizationCycleRunner {
    protected final AnonymizationCycleRunner anonymizationCycleRunner;
    protected final PssType pssType;
    protected final OoiUserGroupMappable ooiUserGroupMapper;

    protected AnonymizationCycleRunnerDecorator(AnonymizationCycleRunner anonymizationCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper) {
        this.anonymizationCycleRunner = anonymizationCycleRunner;
        this.pssType = pssType;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
    }

    @Override
    public List<AnonymizationResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException {
        List<AnonymizationResult> anonymizationResults = new ArrayList<>();
        for (ObservedReport observedReport : observedReports) {
            AnonymizationResult anonymizationResult = anonymizationCycleRunner.runCycle(observedReport);
            anonymizationResults.add(anonymizationResult);
        }
        return anonymizationResults;
    }

    @Override
    public AnonymizationResult runCycle(ObservedReport observedReport) throws PssException {
        return anonymizationCycleRunner.runCycle(observedReport);
    }
}
