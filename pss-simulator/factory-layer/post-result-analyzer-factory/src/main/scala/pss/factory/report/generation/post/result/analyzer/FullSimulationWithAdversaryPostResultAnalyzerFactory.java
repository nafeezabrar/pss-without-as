package pss.factory.report.generation.post.result.analyzer;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.post.result.analyzation.fullsimulation.*;
import pss.result.presentable.printing.TextFileMeanValueWithUnequalSizePrinter;
import pss.result.presentable.printing.TextFileValuePrinter;
import pss.result.presentable.printing.ValuePrintable;

import static pss.config.printing.DefaultPrintingFileName.*;
import static pss.config.printing.ShouldPrintConfig.AppendMode.SHOULD_APPEND;

public class FullSimulationWithAdversaryPostResultAnalyzerFactory {
    public static FullSimulationWithAdversaryPostResultAnalyzer createResultAnalyzer(PostResultProcessConfig postResultProcessConfig, String resultDirectory) {
        FullSimulationWithAdversaryPostResultAnalyzer analyzer = new EmptyAnalyzerWithAdversary();
        analyzer = addAdversaryOwnReportCountAnalyzerIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        analyzer = addAdversaryInterceptedReportCountAnalyzerIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        analyzer = addAdversaryOwnReportedValueCountAnalyzerIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        analyzer = addAdversaryOwnReportedDecodabilityAnalyzerIfNecessary(postResultProcessConfig, analyzer, resultDirectory);
        return analyzer;
    }

    private static FullSimulationWithAdversaryPostResultAnalyzer addAdversaryOwnReportedValueCountAnalyzerIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationWithAdversaryPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printAdversaryReportedValueCount = postResultProcessConfig.isPrintAdversaryOwnReportedValueCount();
        if (printAdversaryReportedValueCount) {
            String resultFileName = getAdversaryReportedValueFileName(resultDirectory);
            analyzer = new AdversaryOwnReportedValueAnalyzer(analyzer, getTextFileValuePrinter(resultFileName));
        }
        return analyzer;
    }

    private static FullSimulationWithAdversaryPostResultAnalyzer addAdversaryOwnReportCountAnalyzerIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationWithAdversaryPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printAdversaryOwnReportCountMap = postResultProcessConfig.isPrintAdversaryOwnReportCountMap();
        if (printAdversaryOwnReportCountMap) {
            String resultFileName = getObservedReportCountOfAdversaryFileName(resultDirectory);
            analyzer = new AdversaryOwnReportAnalyzer(analyzer, getTextFileValuePrinter(resultFileName));
        }
        return analyzer;
    }

    private static FullSimulationWithAdversaryPostResultAnalyzer addAdversaryInterceptedReportCountAnalyzerIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationWithAdversaryPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printAdversaryInterceptedReportCount = postResultProcessConfig.isPrintAdversaryInterceptedReportCount();
        if (printAdversaryInterceptedReportCount) {
            String resultFileName = getInterceptedReportCountFileName(resultDirectory);
            analyzer = new AdversaryInterceptedReportAnalyzer(analyzer, getTextFileValuePrinter(resultFileName));
        }
        return analyzer;
    }

    private static FullSimulationWithAdversaryPostResultAnalyzer addAdversaryOwnReportedDecodabilityAnalyzerIfNecessary(PostResultProcessConfig postResultProcessConfig, FullSimulationWithAdversaryPostResultAnalyzer analyzer, String resultDirectory) {
        boolean printAdversaryOwnReportedDecodability = postResultProcessConfig.isPrintAdversaryOwnReportedDecodability();
        if (printAdversaryOwnReportedDecodability) {
            String resultFileName = getAdversaryOwnDecodabilityFIleName(resultDirectory);
            TextFileMeanValueWithUnequalSizePrinter resultPrinter = new TextFileMeanValueWithUnequalSizePrinter(resultFileName);
            analyzer = new AdversaryOwnReportedDecodabilityAnalyzer(analyzer, resultPrinter);
        }
        return analyzer;
    }

    private static ValuePrintable getTextFileValuePrinter(String fileName) {
        return new TextFileValuePrinter(fileName, SHOULD_APPEND);
    }
}
