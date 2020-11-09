package pss.config.task;

public class FromFileAnonymityGenerationConfig extends AnonymityGenerationConfig {
    protected final String fileName;

    public FromFileAnonymityGenerationConfig(String fileName) {
        super(AnonymityGenerationMethod.FROM_FILE);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
