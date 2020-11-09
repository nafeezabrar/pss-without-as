package pss.runner.cycle.impl.anonymization;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.result.presentable.printing.AnonymizationResultPrintable;
import pss.runner.cycle.anonymization.AnonymizationCycleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnonymizationCycleRunnerWithPrinter extends AnonymizationCycleRunnerDecorator {
    protected final AnonymizationResultPrintable anonymizationResultPrinter;

    public AnonymizationCycleRunnerWithPrinter(AnonymizationCycleRunner anonymizationCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, AnonymizationResultPrintable anonymizationResultPrinter) {
        super(anonymizationCycleRunner, pssType, ooiUserGroupMapper);
        this.anonymizationResultPrinter = anonymizationResultPrinter;
    }


    @Override
    public List<AnonymizationResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException {
        List<AnonymizationResult> anonymizationResults = new ArrayList<>();
        for (ObservedReport observedReport : observedReports) {
            anonymizationResults.add(runCycle(observedReport));
        }
        anonymizationResultPrinter.printAnonymizationResult(pssType, ooiUserGroupMapper, anonymizationResults, observedReports);
        return anonymizationResults;
    }
}
