package pss.config.runner;

import pss.config.adversary.MultipleApsAdversaryConfig;
import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.FULL_SIMULATION_WITH_GENERATED_ADVERSARY_TILL_DECODING;

public class FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig extends RunnerConfig {
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
    protected final MultipleApsAdversaryConfig adversaryConfig;

    public FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, PssGroupingConfig pssGroupingConfig, UserGenerationConfig userGenerationConfig, UserGroupingConfig userGroupingConfig, ObservedReportGenerationConfig observedReportGenerationConfig, AnonymityGenerationConfig anonymityGenerationConfig, AnonymizationConfig anonymizationConfig, DeanonymizationConfig deanonymizationConfig, FinalReportInheritedGenerationConfig finalReportGenerationConfig, FullCycleRunnerConfig cycleRunnerConfig, MultipleApsAdversaryConfig adversaryConfig) {
        super(FULL_SIMULATION_WITH_GENERATED_ADVERSARY_TILL_DECODING, postResultProcessConfig);
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
        this.adversaryConfig = adversaryConfig;
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

    public MultipleApsAdversaryConfig getAdversaryConfig() {
        return adversaryConfig;
    }
}
