package pss.config.adversary.report_filtering;

import java.util.Set;

import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.TARGET_USER_SET_REPORT;

public class TargetUserSetReportFilteringConfig extends ReportFilteringConfig {
    protected final Set<Integer> userIds;

    public TargetUserSetReportFilteringConfig(Set<Integer> userIds) {
        super(TARGET_USER_SET_REPORT);
        this.userIds = userIds;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }
}
