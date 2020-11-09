package pss.factory.report.generation.task;

import pss.config.runner.FullCycleRunnerConfig;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.cycle_runner.anonymization.FullCycleRunnerFactory;
import pss.factory.report.generation.cycle_runner.anonymization.impl.FullCycleRunnerFactoryImpl;

public class FullCycleRunnerFactoryCreator {
    public static FullCycleRunnerFactory createFullCycleRunnerFactory(long runnerSeed, FullCycleRunnerConfig fullCycleRunnerConfig, String resultDirectory) throws RunnerCreationException {
        return new FullCycleRunnerFactoryImpl(runnerSeed, fullCycleRunnerConfig, resultDirectory);
    }
}
