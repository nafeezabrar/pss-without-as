package pss.factory.report.generation.runner;

import pss.config.runner.PssVariableGenerationRunnerConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.task.PssVariableGeneratorFactory;
import pss.runner.PssVariableGeneratingRunner;
import pss.variable.generation.PssVariableGenerable;

public class PssVariableGeneratingRunnerFactory {
    public static PssVariableGeneratingRunner generatePssVariableGeneratingRunner(PssVariableGenerationRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException {

        PssType pssType = runnerConfig.getPssType();
        PssVariableGenerable pssVariableGenerator = PssVariableGeneratorFactory.generatePssVariableGenerator(pssType, runnerConfig.getPssVariableGenerationConfig(), runnerSeed, resultDirectory);
        return new PssVariableGeneratingRunner(pssType, pssVariableGenerator);
    }
}
