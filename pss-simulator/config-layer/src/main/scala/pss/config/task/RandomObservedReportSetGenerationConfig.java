package pss.config.task;

import pss.config.RandomSource;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public class RandomObservedReportSetGenerationConfig extends RandomObservedReportGenerationConfig {
    protected final int reportCount;

    public RandomObservedReportSetGenerationConfig(SaveConfig saveConfig, PrintConfig printConfig, RandomSource randomSource, int reportCount) {
        super(saveConfig, printConfig, randomSource);
        this.reportCount = reportCount;
    }

    public int getReportCount() {
        return reportCount;
    }
}
