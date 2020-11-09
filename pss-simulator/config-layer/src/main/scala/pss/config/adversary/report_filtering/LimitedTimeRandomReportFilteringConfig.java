package pss.config.adversary.report_filtering;

import pss.config.RandomSource;

import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.LIMITED_TIME_RANDOM_REPORTS;

public class LimitedTimeRandomReportFilteringConfig extends ReportFilteringConfig {
    protected final int startReportId;
    protected final int endReportId;
    protected final RandomSource randomSource;
    protected final int totalReports;

    public LimitedTimeRandomReportFilteringConfig(int startReportId, int endReportId, RandomSource randomSource, int totalReports) {
        super(LIMITED_TIME_RANDOM_REPORTS);
        this.startReportId = startReportId;
        this.endReportId = endReportId;
        this.randomSource = randomSource;
        this.totalReports = totalReports;
    }

    public int getStartReportId() {
        return startReportId;
    }

    public int getEndReportId() {
        return endReportId;
    }

    public RandomSource getRandomSource() {
        return randomSource;
    }

    public int getTotalReports() {
        return totalReports;
    }
}
