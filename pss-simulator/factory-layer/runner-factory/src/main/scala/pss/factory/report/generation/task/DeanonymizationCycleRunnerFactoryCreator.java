package pss.factory.report.generation.task;

import pss.exception.RunnerCreationException;
import pss.factory.report.generation.cycle_runner.anonymization.DeanonymizationCycleRunnerFactory;

public class DeanonymizationCycleRunnerFactoryCreator {
    public static DeanonymizationCycleRunnerFactory createDeanonymizationCycleRunnerFactory() throws RunnerCreationException {
        throw new RunnerCreationException("Deanonymization cycle runner factory not implemented");
    }
}
