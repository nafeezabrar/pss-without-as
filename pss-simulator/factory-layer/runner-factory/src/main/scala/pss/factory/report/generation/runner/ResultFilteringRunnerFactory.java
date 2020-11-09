package pss.factory.report.generation.runner;

import pss.config.runner.ResultFilteringRunnerConfig;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.runner.PssRunnable;
import pss.runner.result.MeanStdResultFilteringRunner;

import static pss.config.runner.ResultFilteringRunnerConfig.ResultFilteringType;

public class ResultFilteringRunnerFactory {
    public static PssRunnable generateResultFilteringRunner(ResultFilteringRunnerConfig runnerConfig, long seed) throws RunnerCreationException, PssException {
        ResultFilteringType resultFilteringType = runnerConfig.getResultFilteringType();
        switch (resultFilteringType) {

            case CALCULATE_MEAN_STD:
                return new MeanStdResultFilteringRunner(runnerConfig.getFileName());
            case CALCULATE_MEAN:
                return new MeanStdResultFilteringRunner(runnerConfig.getFileName());
        }
        throw new PssException(String.format("Result filtering type %s not matched", resultFilteringType.toString()));
    }
}
