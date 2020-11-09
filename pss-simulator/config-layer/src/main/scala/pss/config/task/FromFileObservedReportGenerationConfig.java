package pss.config.task;

import pss.ReadingSourceType;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public class FromFileObservedReportGenerationConfig extends ObservedReportGenerationConfig {
    protected final String fileName;
    protected final ReadingSourceType readingSourceType;

    public FromFileObservedReportGenerationConfig(ReportGenerationMethod reportGenerationMethod, SaveConfig saveConfig, PrintConfig printConfig, String fileName, ReadingSourceType readingSourceType) {
        super(reportGenerationMethod, saveConfig, printConfig);
        this.fileName = fileName;
        this.readingSourceType = readingSourceType;
    }

    public String getFileName() {
        return fileName;
    }

    public ReadingSourceType getReadingSourceType() {
        return readingSourceType;
    }
}
