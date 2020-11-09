package pss.config.task;

public class FinalReportInheritedGenerationConfig {
    protected final FinalReportInheritedGenerationMethod finalReportInheritedGenerationMethod;

    public FinalReportInheritedGenerationConfig(FinalReportInheritedGenerationMethod finalReportInheritedGenerationMethod) {
        this.finalReportInheritedGenerationMethod = finalReportInheritedGenerationMethod;
    }

    public FinalReportInheritedGenerationMethod getFinalReportInheritedGenerationMethod() {
        return finalReportInheritedGenerationMethod;
    }

    public enum FinalReportInheritedGenerationMethod {
        SIMPLE,
        SIMPLE_FAULTY,
        RAS_RANDOM, AAS_RANDOM
    }
}
