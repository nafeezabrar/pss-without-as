package pss.runner.cycle.impl.deanonymization;

import pss.exception.PssException;
import pss.report.finalreport.FinalReport;
import pss.result.deanonymization.DeanonymizationResult;
import pss.result.presentable.printing.DeanonymizationResultPrintable;
import pss.runner.cycle.deanonymization.DeanonymizationCycleRunner;

import java.io.IOException;
import java.util.List;

public class DeanonymizationCycleRunnerWithPrinter extends DeanonymizationCycleRunnerDecorator {
    protected final DeanonymizationResultPrintable deanonymizationResultPrinter;

    public DeanonymizationCycleRunnerWithPrinter(DeanonymizationCycleRunner deanonymizationCycleRunner, DeanonymizationResultPrintable deanonymizationResultPrinter) {
        super(deanonymizationCycleRunner);
        this.deanonymizationResultPrinter = deanonymizationResultPrinter;
    }

    @Override
    public List<DeanonymizationResult> runCycle(List<FinalReport> finalReports) throws PssException, IOException {
        List<DeanonymizationResult> deanonymizationResults = super.runCycle(finalReports);
        deanonymizationResultPrinter.printDeanonymizationResult(deanonymizationResults);
        return deanonymizationResults;
    }
}
