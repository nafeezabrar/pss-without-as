package pss.config.task;

public abstract class AnonymityGenerationConfig {
    protected final AnonymityGenerationMethod anonymityGenerationMethod;

    public AnonymityGenerationConfig(AnonymityGenerationMethod anonymityGenerationMethod) {
        this.anonymityGenerationMethod = anonymityGenerationMethod;
    }

    public AnonymityGenerationMethod getAnonymityGenerationMethod() {
        return anonymityGenerationMethod;
    }

    public enum AnonymityGenerationMethod {
        FIXED,
        UNIFORM_ANONYMITY,
        FIXED_AAS,
        FIXED_RAS,
        RANDOM,
        FROM_FILE,
        FROM_FILE_INHERITED
    }
}
