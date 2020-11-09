package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.ANONYMIZING_RUNNER;

public class AnonymizingRunnerConfig extends RunnerConfig {
    protected final PssType pssType;
    protected final PssVariableGenerationConfig pssVariableGenerationConfig;
    protected final PssGroupingConfig pssGroupingConfig;
    protected final UserGenerationConfig userGenerationConfig;
    protected final UserGroupingConfig userGroupingConfig;
    protected final ObservedReportGenerationConfig observedReportGenerationConfig;
    protected final AnonymityGenerationConfig anonymityGenerationConfig;
    protected final AnonymizationConfig anonymizationConfig;
    protected final CycleRunnerConfig cycleRunnerConfig;

    public AnonymizingRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, PssGroupingConfig pssGroupingConfig, UserGenerationConfig userGenerationConfig, UserGroupingConfig userGroupingConfig, ObservedReportGenerationConfig observedReportGenerationConfig, AnonymityGenerationConfig anonymityGenerationConfig, AnonymizationConfig anonymizationConfig, CycleRunnerConfig cycleRunnerConfig) {
        super(ANONYMIZING_RUNNER, postResultProcessConfig);
        this.pssType = pssType;
        this.pssVariableGenerationConfig = pssVariableGenerationConfig;
        this.pssGroupingConfig = pssGroupingConfig;
        this.userGenerationConfig = userGenerationConfig;
        this.userGroupingConfig = userGroupingConfig;
        this.observedReportGenerationConfig = observedReportGenerationConfig;
        this.anonymityGenerationConfig = anonymityGenerationConfig;
        this.anonymizationConfig = anonymizationConfig;
        this.cycleRunnerConfig = cycleRunnerConfig;
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

    public AnonymizationConfig getAnonymizationConfig() {
        return anonymizationConfig;
    }

    public AnonymityGenerationConfig getAnonymityGenerationConfig() {
        return anonymityGenerationConfig;
    }

    public CycleRunnerConfig getCycleRunnerConfig() {
        return cycleRunnerConfig;
    }
}
