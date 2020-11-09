package pss.config.adversary.report_filtering;

import pss.config.RandomSource;

import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.LIMITED_TIME_ALL_REPORTS_PERCENTAGE;

public class LimitedTimeReportFilteringWithPercentageConfig extends ReportFilteringConfig {
    protected final RandomSource randomSource;
    protected final double percentage;

    public LimitedTimeReportFilteringWithPercentageConfig(RandomSource randomSource, double percentage) {
        super(LIMITED_TIME_ALL_REPORTS_PERCENTAGE);
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
