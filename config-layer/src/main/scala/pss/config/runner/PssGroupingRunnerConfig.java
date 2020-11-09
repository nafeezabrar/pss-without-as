package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.PssGroupingConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.PSS_GROUPING_RUNNER;

public class PssGroupingRunnerConfig extends RunnerConfig {
    protected final PssType pssType;
    protected final PssVariableGenerationConfig pssVariableGenerationConfig;
    protected final PssGroupingConfig pssGroupingConfig;

    public PssGroupingRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, PssGroupingConfig pssGroupingConfig) {
        super(PSS_GROUPING_RUNNER, postResultProcessConfig);
        this.pssType = pssType;
        this.pssVariableGenerationConfig = pssVariableGenerationConfig;
        this.pssGroupingConfig = pssGroupingConfig;
    }


    public PssType getPssType() {
        return pssType;
    }

    public PssVariableGenerationConfig getPssVariableGenerationConfig() {
        return pssVariableGenerationConfig;
    }

    public PssGroupingConfig getPssGroupingConfig() {
        return pssGroupingConfig;
    }
}
