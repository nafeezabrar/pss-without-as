package pss.factory.report.generation.cycle_runner.anonymization;

import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.runner.cycle.deanonymization.DeanonymizationCycleRunner;


public interface DeanonymizationCycleRunnerFactory {
    DeanonymizationCycleRunner generateCycleRunner(DeanonymizerSelector deanonymizerSelector);
}
