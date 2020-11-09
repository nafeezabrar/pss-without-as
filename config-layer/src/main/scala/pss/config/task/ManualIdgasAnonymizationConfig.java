package pss.config.task;

import pss.ReadingSourceType;

import static pss.config.task.AnonymizationConfig.AnonymizationMethod.IDGAS_FROM_FILE;

public class ManualIdgasAnonymizationConfig extends AnonymizationConfig {
    protected final ReadingSourceType readingSourceType;
    protected final String fileName;

    public ManualIdgasAnonymizationConfig(ReadingSourceType readingSourceType, String fileName) {
        super(IDGAS_FROM_FILE);
        this.readingSourceType = readingSourceType;
        this.fileName = fileName;
    }

    public ReadingSourceType getReadingSourceType() {
        return readingSourceType;
    }

    public String getFileName() {
        return fileName;
    }
}
