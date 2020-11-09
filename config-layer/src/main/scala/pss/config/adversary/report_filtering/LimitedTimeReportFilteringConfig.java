package pss.config.adversary.report_filtering;

import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.LIMITED_TIME_ALL_REPORTS;

public class LimitedTimeReportFilteringConfig extends ReportFilteringConfig {
    protected final int startReportId;
    protected final int endReportId;

    public LimitedTimeReportFilteringConfig(int startReportId, int endReportId) {
        super(LIMITED_TIME_ALL_REPORTS);
        this.startReportId = startReportId;
        this.endReportId = endReportId;
    }

    public int getStartReportId() {
        return startReportId;
    }

    public int getEndReportId() {
        return endReportId;
    }
}
