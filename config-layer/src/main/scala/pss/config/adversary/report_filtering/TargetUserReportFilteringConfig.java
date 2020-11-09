package pss.config.adversary.report_filtering;

import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.TARGET_USER_REPORT;

public class TargetUserReportFilteringConfig extends ReportFilteringConfig {
    protected final int targetUserId;

    public TargetUserReportFilteringConfig(int targetUserId) {
        super(TARGET_USER_REPORT);
        this.targetUserId = targetUserId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }
}
