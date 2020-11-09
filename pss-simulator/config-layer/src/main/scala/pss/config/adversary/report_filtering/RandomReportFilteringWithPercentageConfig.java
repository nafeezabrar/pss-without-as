package pss.config.adversary.report_filtering;

import pss.config.RandomSource;

public class RandomReportFilteringWithPercentageConfig extends ReportFilteringConfig {
    protected final RandomSource randomSource;
    protected final double percentage;

    public RandomReportFilteringWithPercentageConfig(RandomSource randomSource, double percentage) {
        super(ReportFilteringMethod.RANDOM_REPORTS_PERCENTAGE);
        this.randomSource = randomSource;
        this.percentage = percentage;
    }

    public RandomSource getRandomSource() {
        return randomSource;
    }

    public double getPercentage() {
        return percentage;
    }
}
