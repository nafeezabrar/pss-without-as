package pss.config.task;

import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public abstract class ObservedReportGenerationConfig {
    protected final ReportGenerationMethod reportGenerationMethod;
    protected final SaveConfig saveConfig;
    protected final PrintConfig printConfig;

    protected ObservedReportGenerationConfig(ReportGenerationMethod reportGenerationMethod, SaveConfig saveConfig, PrintConfig printConfig) {
        this.reportGenerationMethod = reportGenerationMethod;
        this.saveConfig = saveConfig;
        this.printConfig = printConfig;
    }


    public ReportGenerationMethod getReportGenerationMethod() {
        return reportGenerationMethod;
    }

    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public PrintConfig getPrintConfig() {
        return printConfig;
    }

    public enum ReportGenerationMethod {
        FROM_FILE,
        RANDOM
    }
}
