package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.PSS_VARIABLE_GENERATING_RUNNER;

public class PssVariableGenerationRunnerConfig extends RunnerConfig {
    protected final PssType pssType;
    protected final PssVariableGenerationConfig pssVariableGenerationConfig;

    public PssVariableGenerationRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig) {
        super(PSS_VARIABLE_GENERATING_RUNNER, postResultProcessConfig);
        this.pssType = pssType;
        this.pssVariableGenerationConfig = pssVariableGenerationConfig;
    }


    public PssType getPssType() {
        return pssType;
    }

    public PssVariableGenerationConfig getPssVariableGenerationConfig() {
        return pssVariableGenerationConfig;
    }
}
