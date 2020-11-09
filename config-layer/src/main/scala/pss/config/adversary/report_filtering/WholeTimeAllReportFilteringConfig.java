package pss.config.adversary.report_filtering;

import static pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod.ALL_REPORTS;

public class WholeTimeAllReportFilteringConfig extends ReportFilteringConfig {
    public WholeTimeAllReportFilteringConfig() {
        super(ALL_REPORTS);
    }
}
