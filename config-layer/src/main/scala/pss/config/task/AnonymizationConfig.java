package pss.config.task;

public class AnonymizationConfig {
    protected final AnonymizationMethod anonymizationMethod;

    public AnonymizationConfig(AnonymizationMethod anonymizationMethod) {
        this.anonymizationMethod = anonymizationMethod;
    }

    public AnonymizationMethod getAnonymizationMethod() {
        return anonymizationMethod;
    }

    public enum AnonymizationMethod {
        IDGAS,
        AAS,
        RAS,
        IDGAS_FROM_FILE,
        DGAS
    }
}
