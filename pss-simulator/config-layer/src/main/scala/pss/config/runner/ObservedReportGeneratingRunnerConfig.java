package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.REPORT_GENERATING_RUNNER;

public class ObservedReportGeneratingRunnerConfig extends RunnerConfig {
    protected final PssType pssType;
    protected final PssVariableGenerationConfig pssVariableGenerationConfig;
    protected final PssGroupingConfig pssGroupingConfig;
    protected final UserGenerationConfig userGenerationConfig;
    protected final UserGroupingConfig userGroupingConfig;
    protected final ObservedReportGenerationConfig observedReportGenerationConfig;

    public ObservedReportGeneratingRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, PssGroupingConfig pssGroupingConfig, UserGenerationConfig userGenerationConfig, UserGroupingConfig userGroupingConfig, ObservedReportGenerationConfig observedReportGenerationConfig) {
        super(REPORT_GENERATING_RUNNER, postResultProcessConfig);
        this.pssType = pssType;
        this.pssVariableGenerationConfig = pssVariableGenerationConfig;
        this.pssGroupingConfig = pssGroupingConfig;
        this.userGenerationConfig = userGenerationConfig;
        this.userGroupingConfig = userGroupingConfig;
        this.observedReportGenerationConfig = observedReportGenerationConfig;
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

    public UserGenerationConfig getUserGenerationConfig() {
        return userGenerationConfig;
    }

    public UserGroupingConfig getUserGroupingConfig() {
        return userGroupingConfig;
    }

    public ObservedReportGenerationConfig getObservedReportGenerationConfig() {
        return observedReportGenerationConfig;
    }
}
