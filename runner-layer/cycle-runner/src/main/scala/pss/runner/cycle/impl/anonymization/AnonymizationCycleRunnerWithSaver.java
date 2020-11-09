package pss.runner.cycle.impl.anonymization;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.runner.cycle.anonymization.AnonymizationCycleRunner;
import pss.saving.anonymization_result.AnonymizationResultSavable;

import java.io.IOException;
import java.util.List;

public class AnonymizationCycleRunnerWithSaver extends AnonymizationCycleRunnerDecorator {
    protected final AnonymizationResultSavable anonymizationResultSaver;

    public AnonymizationCycleRunnerWithSaver(AnonymizationCycleRunner anonymizationCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, AnonymizationResultSavable anonymizationResultSaver) {
        super(anonymizationCycleRunner, pssType, ooiUserGroupMapper);
        this.anonymizationResultSaver = anonymizationResultSaver;
    }

    @Override
    public List<AnonymizationResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException {
        List<AnonymizationResult> anonymizationResults = super.runCycle(observedReports);
        anonymizationResultSaver.saveAnonymizationResult(anonymizationResults);
        return anonymizationResults;
    }
}
