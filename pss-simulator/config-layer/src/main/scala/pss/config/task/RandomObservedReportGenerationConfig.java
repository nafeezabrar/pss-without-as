package pss.config.task;

import pss.config.RandomSource;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

import static pss.config.task.ObservedReportGenerationConfig.ReportGenerationMethod.RANDOM;

public class RandomObservedReportGenerationConfig extends ObservedReportGenerationConfig {
    protected final RandomSource randomSource;

    public RandomObservedReportGenerationConfig(SaveConfig saveConfig, PrintConfig printConfig, RandomSource randomSource) {
        super(RANDOM, saveConfig, printConfig);
        this.randomSource = randomSource;
    }

    public RandomSource getRandomSource() {
        return randomSource;
    }
}
