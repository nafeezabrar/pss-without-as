package pss.config.adversary.report_filtering;

import pss.config.RandomSource;

public class RandomReportFilteringConfig extends ReportFilteringConfig {
    protected final RandomSource randomSource;
    protected final int totalReports;

    public RandomReportFilteringConfig(RandomSource randomSource, int totalReports) {
        super(ReportFilteringMethod.RANDOM_REPORTS);
        this.randomSource = randomSource;
        this.totalReports = totalReports;
    }

    public RandomSource getRandomSource() {
        return randomSource;
    }

    public int getTotalReports() {
        return totalReports;
    }
}
