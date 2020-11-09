package pss.runner.cycle.impl.deanonymization;

import pss.exception.PssException;
import pss.report.finalreport.FinalReport;
import pss.result.deanonymization.DeanonymizationResult;
import pss.runner.cycle.deanonymization.DeanonymizationCycleRunner;
import pss.saving.deanonymization_result.DeanonymizationResultSavable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeanonymizationCycleRunnerWithSaver extends DeanonymizationCycleRunnerDecorator {
    protected final DeanonymizationResultSavable deanonymizationResultSaver;

    public DeanonymizationCycleRunnerWithSaver(DeanonymizationCycleRunner deanonymizationCycleRunner, DeanonymizationResultSavable deanonymizationResultSaver) {
        super(deanonymizationCycleRunner);
        this.deanonymizationResultSaver = deanonymizationResultSaver;
    }

    @Override
    public List<DeanonymizationResult> runCycle(List<FinalReport> finalReports) throws PssException, IOException {
        List<DeanonymizationResult> deanonymizationResults = new ArrayList<>();
        for (FinalReport finalReport : finalReports) {
            DeanonymizationResult deanonymizationResult = super.runCycle(finalReport);
            deanonymizationResults.add(deanonymizationResult);
        }
        return deanonymizationResults;
    }
}
