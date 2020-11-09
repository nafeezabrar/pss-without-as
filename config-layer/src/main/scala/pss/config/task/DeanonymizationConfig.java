package pss.config.task;

public class DeanonymizationConfig {
    protected final DeanonymizationMethod deanonymizationMethod;

    public DeanonymizationConfig(DeanonymizationMethod deanonymizationMethod) {
        this.deanonymizationMethod = deanonymizationMethod;
    }

    public DeanonymizationMethod getDeanonymizationMethod() {
        return deanonymizationMethod;
    }

    public enum DeanonymizationMethod {
        IDGAS,
        DGAS
    }
}
