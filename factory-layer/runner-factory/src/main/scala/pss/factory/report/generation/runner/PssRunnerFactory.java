package pss.factory.report.generation.runner;

import pss.config.runner.*;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.exception.RunnerCreationException;
import pss.runner.PssRunnable;

public class PssRunnerFactory {

    public static PssRunnable generatePssRunner(RunnerConfig runnerConfig, long seed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException, ReaderException {
        RunnerType runnerType = runnerConfig.getRunnerType();
        switch (runnerType) {
            case PSS_VARIABLE_GENERATING_RUNNER:
                return PssVariableGeneratingRunnerFactory.generatePssVariableGeneratingRunner((PssVariableGenerationRunnerConfig) runnerConfig, seed, resultDirectory);
            case PSS_GROUPING_RUNNER:
                return PssGroupingRunnerFactory.generatePssGroupingRunner((PssGroupingRunnerConfig) runnerConfig, seed, resultDirectory);
            case USER_GENERATING_RUNNER:
                return UserGeneratingRunnerFactory.generateUserGeneratingRunner((UserGeneratingRunnerConfig) runnerConfig, seed, resultDirectory);
            case USER_GROUPING_RUNNER:
                return UserGroupingRunnerFactory.generateUserGeneratingRunner((UserGroupingRunnerConfig) runnerConfig, seed, resultDirectory);
            case REPORT_GENERATING_RUNNER:
                return ObservedReportGeneratingRunnerFactory.generateObservedReportGeneratingRunner((ObservedReportGeneratingRunnerConfig) runnerConfig, seed, resultDirectory);
            case ANONYMIZING_RUNNER:
                return AnonymizingRunnerFactory.generateAnonymizingRunner((AnonymizingRunnerConfig) runnerConfig, seed, resultDirectory);
            case DEANONYMIZING_RUNNER:
                return DeanonymizingRunnerFactory.generateDeanonymizingRunner((DeanonymizingRunnerConfig) runnerConfig, seed, resultDirectory);
            case FULL_SIMULATION_RUNNER:
                return FullSimulationRunnerFactory.generateFullSimulationRunner((FullSimulationRunnerConfig) runnerConfig, seed, resultDirectory);
            case RESULT_FILTERING_RUNNER:
                return ResultFilteringRunnerFactory.generateResultFilteringRunner((ResultFilteringRunnerConfig) runnerConfig, seed);

            case FULL_SIMULATION_WITH_ADVERSARY:
                return FullSimulationWithAdversaryRunnerFactory.generateFullSimulationWithAdversaryRunner((FullSimulationWithAdversaryRunnerConfig) runnerConfig, seed, resultDirectory);
            case FULL_SIMULATION_WITH_GENERATED_ADVERSARY:
                return FullSimulationWithGeneratedAdversaryRunnerFactory.generateFullSimulationWithAdversaryRunner((FullSimulationWithGeneratedAdversaryRunnerConfig) runnerConfig, seed, resultDirectory);
            case FULL_SIMULATION_WITH_GENERATED_ADVERSARY_TILL_DECODING:
                return FullSimulationWithGeneratedAdversaryTillDecodingRunnerFactory.generateFullSimulationWithGeneratedAdversaryTillDecodingRunner((FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig) runnerConfig, seed, resultDirectory);
            case FULL_SIMULATION_WITH_ADVERSARY_TILL_DECODING:
                return FullSimulationWithAdversaryTillDecodingRunnerFactory.generateFullSimulationWithAdversaryTillDecodingRunner((FullSimulationWithAdversaryTillDecodingRunnerConfig) runnerConfig, seed, resultDirectory);
            case FULL_SIMULATION_TILL_DECODING:
                return FullSimulationTillDecodingRunnerFactory.generateFullSimulationTillDecodingRunner((FullSimulationTillDecodingRunnerConfig) runnerConfig, seed, resultDirectory);
        }
        throw new RunnerCreationException(String.format("Runner creation not implemented for %s", runnerType.toString()));
    }
}
