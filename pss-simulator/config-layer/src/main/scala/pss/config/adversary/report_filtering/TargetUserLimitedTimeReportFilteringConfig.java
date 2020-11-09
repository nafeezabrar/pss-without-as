package pss.config.adversary.report_filtering;

import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.TARGET_USER_LIMITED_TIME;

public class TargetUserLimitedTimeReportFilteringConfig extends ReportFilteringConfig {
    protected final int startReportId;
    protected final int endReportId;
    protected final int targetUserId;

    public TargetUserLimitedTimeReportFilteringConfig(int startReportId, int endReportId, int targetUserId) {
        super(TARGET_USER_LIMITED_TIME);
        this.startReportId = startReportId;
        this.endReportId = endReportId;
        this.targetUserId = targetUserId;
    }

    public int getStartReportId() {
        return startReportId;
    }

    public int getEndReportId() {
        return endReportId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }
}
