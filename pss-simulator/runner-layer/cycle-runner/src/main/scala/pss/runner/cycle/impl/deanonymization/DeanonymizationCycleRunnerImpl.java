package pss.runner.cycle.impl.deanonymization;

import pss.deanonymization.Deanonymizable;
import pss.report.finalreport.FinalReport;
import pss.result.deanonymization.DeanonymizationResult;
import pss.runner.cycle.deanonymization.DeanonymizationCycleRunner;

import java.util.ArrayList;
import java.util.List;

public class DeanonymizationCycleRunnerImpl implements DeanonymizationCycleRunner {

    protected final Deanonymizable deanonymizer;

    public DeanonymizationCycleRunnerImpl(Deanonymizable deanonymizer) {
        this.deanonymizer = deanonymizer;
    }

    @Override
    public List<DeanonymizationResult> runCycle(List<FinalReport> finalReports) {
        List<DeanonymizationResult> deanonymizationResults = new ArrayList<>();
        for (FinalReport finalReport : finalReports) {
            DeanonymizationResult deanonymizationResult = runCycle(finalReport);
            deanonymizationResults.add(deanonymizationResult);
        }
        return deanonymizationResults;
    }

    @Override
    public DeanonymizationResult runCycle(FinalReport finalReport) {
        return deanonymizer.deanonymize(finalReport);
    }
}
