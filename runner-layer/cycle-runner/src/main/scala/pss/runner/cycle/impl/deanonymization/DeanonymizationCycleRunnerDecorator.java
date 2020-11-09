package pss.runner.cycle.impl.deanonymization;

import pss.exception.PssException;
import pss.report.finalreport.FinalReport;
import pss.result.deanonymization.DeanonymizationResult;
import pss.runner.cycle.deanonymization.DeanonymizationCycleRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeanonymizationCycleRunnerDecorator implements DeanonymizationCycleRunner {
    protected final DeanonymizationCycleRunner deanonymizationCycleRunner;

    public DeanonymizationCycleRunnerDecorator(DeanonymizationCycleRunner deanonymizationCycleRunner) {
        this.deanonymizationCycleRunner = deanonymizationCycleRunner;
    }

    @Override
    public List<DeanonymizationResult> runCycle(List<FinalReport> finalReports) throws PssException, IOException {
        List<DeanonymizationResult> deanonymizationResults = new ArrayList<>();
        for (FinalReport finalReport : finalReports) {
            DeanonymizationResult deanonymizationResult = deanonymizationCycleRunner.runCycle(finalReport);
            deanonymizationResults.add(deanonymizationResult);
        }
        return deanonymizationResults;
    }

    @Override
    public DeanonymizationResult runCycle(FinalReport report) throws PssException {
        return deanonymizationCycleRunner.runCycle(report);
    }
}
