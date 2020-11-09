package pss.factory.report.generation.post.result.analyzer;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.post.result.analyzation.fullsimulation.*;
import pss.result.presentable.printing.TextFileApsApsDecodabilityResultPrinter;
import pss.result.presentable.printing.TextFileCountByUserPrinter;
import pss.result.presentable.printing.TextFileReportCountByGroupPrinter;
import pss.result.presentable.printing.TextFileValuePrinter;

import static pss.config.printing.DefaultPrintingFileName.*;
import static pss.config.printing.ShouldPrintConfig.AppendMode.SHOULD_APPEND;

public class FullSimulationPostResultAnalyzerFactory {
    public static FullSimulationPostResultAnalyzer createResultAnalyzer(PostResultProcessConfig postResultProcessConfig, String resultDirectory) {
        FullSimulationPostResultAnalyzer analyzer = new EmptyAnalyzer();
        analyzer = addObservedReportCountAnalyzerIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        analyzer = addObservedReportValueCountAnalyzerIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        analyzer = addDecodabilityAnalyzerIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        analyzer = addReportCountToDecodeIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        analyzer = addReportCountOfEachGroupIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        return analyzer;
    }

    private static FullSimulationPostResultAnalyzer addReportCountOfEachGroupIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printReportCountOfEachGroup = postResultProcessConfig.isPrintReportCountOfEachGroup();
        if (printReportCountOfEachGroup) {
            TextFileReportCountByGroupPrinter resultPrinter = new TextFileReportCountByGroupPrinter(getReportCountByGroupFileName(resultDirectory), SHOULD_APPEND);
            analyzer = new ObservedReportByGroupAnalyzer(analyzer, resultPrinter);
        }
        return analyzer;
    }

    private static FullSimulationPostResultAnalyzer addReportCountToDecodeIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printReportCountToDecode = postResultProcessConfig.isPrintReportCountToDecode();
        if (printReportCountToDecode) {
            TextFileValuePrinter valuePrinter = new TextFileValuePrinter(getReportCountToDecodeFileName(resultDirectory), SHOULD_APPEND);
            analyzer = new ReportCountForDecodabilityAnalyzer(analyzer, valuePrinter);
        }
        return analyzer;
    }

    private static FullSimulationPostResultAnalyzer addDecodabilityAnalyzerIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printDecodabilityAnalysis = postResultProcessConfig.isPrintDecodabilityAnalysis();
        if (printDecodabilityAnalysis) {
            TextFileApsApsDecodabilityResultPrinter resultPrinter = new TextFileApsApsDecodabilityResultPrinter(getDecodabilityResultOfApsFileName(resultDirectory), SHOULD_APPEND);
            analyzer = new ApsDecodabilityAnalyzer(analyzer, resultPrinter);
        }
        return analyzer;
    }

    private static FullSimulationPostResultAnalyzer addObservedReportCountAnalyzerIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printReportCountMap = postResultProcessConfig.isPrintReportCountMap();
        if (printReportCountMap) {
            TextFileCountByUserPrinter reportCountPrinter = new TextFileCountByUserPrinter(getReportCountByUserFileName(resultDirectory), SHOULD_APPEND);
            analyzer = new ObservedReportCountByUserAnalyzer(analyzer, reportCountPrinter);
        }
        return analyzer;
    }

    private static FullSimulationPostResultAnalyzer addObservedReportValueCountAnalyzerIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printReportedValueCountMap = postResultProcessConfig.isPrintReportedValueCountMap();
        if (printReportedValueCountMap) {
            TextFileCountByUserPrinter printer = new TextFileCountByUserPrinter(getReportedValueCountByUserFileName(resultDirectory), SHOULD_APPEND);
            analyzer = new ObservedReportedValueCountByUserAnalyzer(analyzer, printer);
        }
        return analyzer;
    }
}
