package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.FULL_SIMULATION_TILL_DECODING;

public class FullSimulationTillDecodingRunnerConfig extends RunnerConfig {
    protected final PssType pssType;
    protected final PssVariableGenerationConfig pssVariableGenerationConfig;
    protected final PssGroupingConfig pssGroupingConfig;
    protected final UserGenerationConfig userGenerationConfig;
    protected final UserGroupingConfig userGroupingConfig;
    protected final ObservedReportGenerationConfig observedReportGenerationConfig;
    protected final AnonymityGenerationConfig anonymityGenerationConfig;
    protected final AnonymizationConfig anonymizationConfig;
    protected final DeanonymizationConfig deanonymizationConfig;
    protected final FinalReportInheritedGenerationConfig finalReportGenerationConfig;
    protected final FullCycleRunnerConfig cycleRunnerConfig;

    public FullSimulationTillDecodingRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, PssGroupingConfig pssGroupingConfig, UserGenerationConfig userGenerationConfig, UserGroupingConfig userGroupingConfig, ObservedReportGenerationConfig observedReportGenerationConfig, AnonymityGenerationConfig anonymityGenerationConfig, AnonymizationConfig anonymizationConfig, DeanonymizationConfig deanonymizationConfig, FinalReportInheritedGenerationConfig finalReportGenerationConfig, FullCycleRunnerConfig cycleRunnerConfig) {
        super(FULL_SIMULATION_TILL_DECODING, postResultProcessConfig);
        this.pssType = pssType;
        this.pssVariableGenerationConfig = pssVariableGenerationConfig;
        this.pssGroupingConfig = pssGroupingConfig;
        this.userGenerationConfig = userGenerationConfig;
        this.userGroupingConfig = userGroupingConfig;
        this.observedReportGenerationConfig = observedReportGenerationConfig;
        this.anonymityGenerationConfig = anonymityGenerationConfig;
        this.anonymizationConfig = anonymizationConfig;
        this.deanonymizationConfig = deanonymizationConfig;
        this.finalReportGenerationConfig = finalReportGenerationConfig;
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

    public DeanonymizationConfig getDeanonymizationConfig() {
        return deanonymizationConfig;
    }

    public AnonymityGenerationConfig getAnonymityGenerationConfig() {
        return anonymityGenerationConfig;
    }

    public FullCycleRunnerConfig getCycleRunnerConfig() {
        return cycleRunnerConfig;
    }

    public FinalReportInheritedGenerationConfig getFinalReportGenerationConfig() {
        return finalReportGenerationConfig;
    }
}
