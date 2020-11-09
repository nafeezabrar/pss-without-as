package pss.config.printing;

import static pss.config.printing.ShouldPrintConfig.FileNameMode.GIVEN;

public class DefaultPrintingFileName {
    private static final String directorySeperator = "/";
    private static final String ocTableSaveFileName = "oc_tables";
    private static final String fullCycleResultFileName = "full_cycle_results";
    private static final String adverasryResultFileName = "adversary_results";
    private static final String adversaryDecodabilityResultFileName = "adversary_decodability_result";
    private static final String adversaryIntermediateDecodabilityResultFileName = "adversary_intermediate_decodability_result";
    private static final String intermediateDecodabilityResultFileName = "adversary_intermediate_decodability_result";
    private static final String anonymizationResultFileName = "anonymization_result";
    private static final String userResultFileName = "user_result";
    private static final String userGroupResultFileName = "user_result";
    private static final String pssVariablesResultFileName = "pss_variables_result";
    private static final String pssGroupingResultFileName = "pss_grouping_result";
    private static final String observedReportResultFileName = "observed_reports_result";
    private static final String observedReportCountByAdversaryFileName = "adversary_own_report_count";
    private static final String adversaryDecodedValueCountFileName = "adversary_decoded_value_count";
    private static final String adversaryReportedValueFileName = "adversary_own_reported_values";
    private static final String adversaryReportedDecodabilityFileName = "adversary_own_reported_decodability";
    private static final String reportCountByUserFileName = "report_counts_per_user";
    private static final String reportCountByGroupFileName = "report_counts_by_group";
    private static final String reportedValueCountByUserFileName = "reported_value_counts";
    private static final String decodabilityResultOfApsFileName = "aps_decodability_result.txt";
    private static final String interceptedReportCountFileName = "adversary_intercepted_count";

    private static final String resultSummaryFileName = "result_summary.txt";
    private static final String targetUserDecodabilityResultFileName = "target_decodability";
    private static final String reportCountToDecodeFileName = "report_to_full_decode";

    public static String getObservedReportResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + observedReportResultFileName;
        }
    }

    public static String getPssGroupingResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + pssGroupingResultFileName;
        }
    }

    public static String getPssVariablesResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + pssVariablesResultFileName;
        }
    }

    public static String getUserGroupResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + userGroupResultFileName;
        }
    }

    public static String getUserResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + userResultFileName;
        }
    }

    public static String getAnonymizationResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + anonymizationResultFileName;
        }
    }

    public static String getFullCycleResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + fullCycleResultFileName;
        }
    }

    public static String getIntermediateAdversaryDecodabilityResultFileName(ShouldPrintConfig fullCycleResultPrintConfig, String resultDirectory) {
        if (fullCycleResultPrintConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) fullCycleResultPrintConfig;
            return resultDirectory + directorySeperator + printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + intermediateDecodabilityResultFileName;
        }
    }

    public static String getIntermediateAdversaryDecodabilityResultFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + intermediateDecodabilityResultFileName;
    }

    public static String getEndPointAdversaryDecodabilityPrintFileName(ShouldPrintConfig printConfig, String resultDirectory) {
        if (printConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) printConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + adversaryDecodabilityResultFileName;
        }
    }

    public static String getEndPointAdversaryDecodabilityPrintFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + adversaryDecodabilityResultFileName;
    }

    public static String getAdversaryResultFileName(ShouldPrintConfig printConfig, String resultDirectory) {
        if (printConfig.getFileNameMode() == GIVEN) {
            ShouldPrintConfigWithFileName printConfigWithFileName = (ShouldPrintConfigWithFileName) printConfig;
            return printConfigWithFileName.getFileName();
        } else {
            return resultDirectory + directorySeperator + adverasryResultFileName;
        }
    }

    public static String getAdversaryReportedValueFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + adversaryReportedValueFileName;
    }

    public static String getObservedReportCountOfAdversaryFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + observedReportCountByAdversaryFileName;
    }

    public static String getReportCountByUserFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + reportCountByUserFileName;
    }

    public static String getReportedValueCountByUserFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + reportedValueCountByUserFileName;
    }

    public static String getInterceptedReportCountFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + interceptedReportCountFileName;
    }

    public static String getResultSummaryFileName() {
        return resultSummaryFileName;
    }

    public static String getDecodabilityResultOfApsFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + decodabilityResultOfApsFileName;
    }

    public static String getTargetUserDecodabilityResultFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + targetUserDecodabilityResultFileName;
    }

    public static String getReportCountToDecodeFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + reportCountToDecodeFileName;
    }

    public static String getReportCountByGroupFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + reportCountByGroupFileName;
    }

    public static String getAdversaryDecodedValueCountFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + adversaryDecodedValueCountFileName;
    }

    public static String getAdversaryOwnDecodabilityFIleName(String resultDirectory) {
        return resultDirectory + directorySeperator + adversaryReportedDecodabilityFileName;
    }

    public static String getOcTableSaveFileName(String resultDirectory) {
        return resultDirectory + directorySeperator + ocTableSaveFileName;
    }

    public static String getOcTableSaveFileName() {
        return ocTableSaveFileName;
    }
}
