package pss.config.adversary.report_filtering;

public abstract class ReportFilteringConfig {
    protected final ReportFilteringMethod reportFilteringMethod;

    protected ReportFilteringConfig(ReportFilteringMethod reportFilteringMethod) {
        this.reportFilteringMethod = reportFilteringMethod;
    }

    public ReportFilteringMethod getReportFilteringMethod() {
        return reportFilteringMethod;
    }

    public enum ReportFilteringMethod {
        ALL_REPORTS,
        RANDOM_REPORTS,
        RANDOM_REPORTS_PERCENTAGE,
        LIMITED_TIME_ALL_REPORTS,
        LIMITED_TIME_ALL_REPORTS_PERCENTAGE,
        LIMITED_TIME_RANDOM_REPORTS,
        TARGET_USER_REPORT,
        TARGET_USER_SET_REPORT,
        TARGET_USER_LIMITED_TIME
    }
}
