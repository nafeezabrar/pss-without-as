package pss.config.task;

public class FinalReportDirectGenerationConfig {
    protected final FinalReportDirectGenerationMethod finalReportDirectGenerationMethod;

    public FinalReportDirectGenerationConfig(FinalReportDirectGenerationMethod finalReportDirectGenerationMethod) {
        this.finalReportDirectGenerationMethod = finalReportDirectGenerationMethod;
    }

    public FinalReportDirectGenerationMethod getFinalReportDirectGenerationMethod() {
        return finalReportDirectGenerationMethod;
    }

    public enum FinalReportDirectGenerationMethod {
        FROM_FILE
    }
}
