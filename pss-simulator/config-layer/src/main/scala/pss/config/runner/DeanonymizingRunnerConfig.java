package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.DEANONYMIZING_RUNNER;

public class DeanonymizingRunnerConfig extends RunnerConfig {
    protected final PssType pssType;
    protected final PssVariableGenerationConfig pssVariableGenerationConfig;
    protected final PssGroupingConfig pssGroupingConfig;
    protected final UserGenerationConfig userGenerationConfig;
    protected final UserGroupingConfig userGroupingConfig;
    protected final FinalReportDirectGenerationConfig finalReportGenerationConfig;
    protected final DeanonymizationConfig deanonymizationConfig;

    public DeanonymizingRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, PssGroupingConfig pssGroupingConfig, UserGenerationConfig userGenerationConfig, UserGroupingConfig userGroupingConfig, FinalReportDirectGenerationConfig finalReportGenerationConfig, DeanonymizationConfig deanonymizationConfig) {
        super(DEANONYMIZING_RUNNER, postResultProcessConfig);
        this.pssType = pssType;
        this.pssVariableGenerationConfig = pssVariableGenerationConfig;
        this.pssGroupingConfig = pssGroupingConfig;
        this.userGenerationConfig = userGenerationConfig;
        this.userGroupingConfig = userGroupingConfig;
        this.finalReportGenerationConfig = finalReportGenerationConfig;
        this.deanonymizationConfig = deanonymizationConfig;
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

    public FinalReportDirectGenerationConfig getFinalReportGenerationConfig() {
        return finalReportGenerationConfig;
    }

    public DeanonymizationConfig getDeanonymizationConfig() {
        return deanonymizationConfig;
    }

    public UserGenerationConfig getUserGenerationConfig() {
        return userGenerationConfig;
    }

    public UserGroupingConfig getUserGroupingConfig() {
        return userGroupingConfig;
    }
}
