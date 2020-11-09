package pss.factory.report.generation.task;

import pss.config.runner.CycleRunnerConfig;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.cycle_runner.anonymization.AnonymizationCycleRunnerFactory;
import pss.factory.report.generation.cycle_runner.anonymization.impl.AnonymizationCycleRunnerFactoryImpl;

public class AnonymizationCycleRunnerFactoryCreator {
    public static AnonymizationCycleRunnerFactory createAnonymizationCycleRunnerFactory(CycleRunnerConfig cycleRunnerConfig, String resultDirectory) throws RunnerCreationException {
        return new AnonymizationCycleRunnerFactoryImpl(cycleRunnerConfig, resultDirectory);
    }
}
