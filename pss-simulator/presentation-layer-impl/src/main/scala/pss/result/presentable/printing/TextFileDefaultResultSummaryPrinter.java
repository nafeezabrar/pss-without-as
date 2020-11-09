package pss.result.presentable.printing;

import pss.config.printing.DefaultPrintingFileName;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.library.statistics.StatisticsUtility;
import pss.reader.text.TextFileReader;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileDefaultResultSummaryPrinter implements ResultSummaryPrintable {
    private static final String meanReportCountPrefix = "Mean report count by each user is";
    private static final String meanReportedValueCountPrefix = "Mean reported value count by each user is";
    private static final String meanReportCountToFullDecodePrefix = "Mean Report count to full decode";
    private static final String meanInterceptedReportByAdversaryPrefix = "Mean of intercepted report count by adversary";
    private static final String meanReportCountByAdversaryPrefix = "Mean of own report count by adversary";
    private static final String meanDecodabilityOfAdversaryPrefix = "Mean decodability of adversary";
    private static final String sigmaDecodabilityOfAdversaryPrefix = "Sigma of decodability of adversary";
    private static final String twoSigmaDecodabilityOfAdversaryPrefix = "Two Sigma of decodability of adversary";
    private static final String meanDecodedValueCountOfAdversaryPrefix = "Mean of decoded value count of adversary";
    private static final String meanAdversaryReportedValueCountPrefix = "Mean count of reported value by adversary";
    private static final String meanTargetUserDecodability = "Mean of target user decodability";
    private static final String meanReportCountPerGroupPrefix = "Mean Report Count per group";
    protected final String resultDirectoryName;
    protected final String fileName;
    protected final AppendMode appendMode;


    public TextFileDefaultResultSummaryPrinter(String resultDirectoryName, String fileName, AppendMode appendMode) {
        this.resultDirectoryName = resultDirectoryName;
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printResultSummary() throws PssException, IOException, URISyntaxException {
        FileWriter fileWriter = FileUtility.createFileWriteSafelyInAppendMode(resultDirectoryName + "/" + fileName);
        TextFileTextResultWriter textFileTextResultWriter = new TextFileTextResultWriter(fileWriter);
        List<String> results = new ArrayList<>();
        results.add(getMeanReportCountByEachUserResult());
        results.add(getMeanReportedValueCountByEachUserResult());
        results.add(getMeanReportCountPerGroup());
        results.add(getMeanReportCountToFullDecode());
        results.add(getMeanInterceptedReportCountByAdversary());
        results.add(getMeanReportByAdversary());
        results.add(getMeanDecodabilityByAdversary());
        results.add(getSigmaDecodabilityByAdversary());
        results.add(getTwoSigmaDecodabilityByAdversary());
        results.add(getDecodedValueCountOfAdversary());
        results.add(getMeanAdversaryReportedValueCount());
        results.add(getMeanTargetUserDecodability());

        textFileTextResultWriter.writeResult(new TextResult(results));
    }

    private String getMeanReportCountPerGroup() throws FileNotFoundException {
        String resultFileName = DefaultPrintingFileName.getReportCountByGroupFileName(resultDirectoryName);
        return getMeanCountByIgnoringIteration(resultFileName, meanReportCountPerGroupPrefix);
    }

    private String getMeanReportCountToFullDecode() throws FileNotFoundException {
        String resultFileName = DefaultPrintingFileName.getReportCountToDecodeFileName(resultDirectoryName);
        return getMeanCount(resultFileName, meanReportCountToFullDecodePrefix);
    }

    private String getDecodedValueCountOfAdversary() throws FileNotFoundException {
        String resultFileName = DefaultPrintingFileName.getAdversaryDecodedValueCountFileName(resultDirectoryName);
        return getMeanCount(resultFileName, meanDecodedValueCountOfAdversaryPrefix);
    }

    private String getMeanTargetUserDecodability() throws FileNotFoundException {
        String resultFileName = DefaultPrintingFileName.getTargetUserDecodabilityResultFileName(resultDirectoryName);
        return getMeanCount(resultFileName, meanTargetUserDecodability);
    }

    private String getMeanAdversaryReportedValueCount() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getAdversaryReportedValueFileName(resultDirectoryName);
        return getMeanCount(fileName, meanAdversaryReportedValueCountPrefix);
    }

    private String getMeanDecodabilityByAdversary() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getEndPointAdversaryDecodabilityPrintFileName(resultDirectoryName);
        return getMeanCount(fileName, meanDecodabilityOfAdversaryPrefix);
    }

    private String getSigmaDecodabilityByAdversary() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getEndPointAdversaryDecodabilityPrintFileName(resultDirectoryName);
        return getSigmaCount(fileName, sigmaDecodabilityOfAdversaryPrefix, 1);
    }

    private String getTwoSigmaDecodabilityByAdversary() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getEndPointAdversaryDecodabilityPrintFileName(resultDirectoryName);
        return getSigmaCount(fileName, twoSigmaDecodabilityOfAdversaryPrefix, 2);
    }

    private String getMeanReportByAdversary() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getObservedReportCountOfAdversaryFileName(resultDirectoryName);
        return getMeanCount(fileName, meanReportCountByAdversaryPrefix);
    }

    private String getMeanInterceptedReportCountByAdversary() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getInterceptedReportCountFileName(resultDirectoryName);
        return getMeanCount(fileName, meanInterceptedReportByAdversaryPrefix);
    }


    private String getMeanReportCountByEachUserResult() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getReportCountByUserFileName(resultDirectoryName);
        return getMeanCount(fileName, meanReportCountPrefix);
    }

    private String getMeanReportedValueCountByEachUserResult() throws FileNotFoundException {
        String fileName = DefaultPrintingFileName.getReportedValueCountByUserFileName(resultDirectoryName);
        return getMeanCount(fileName, meanReportedValueCountPrefix);
    }

    private String getMeanCount(String fileName, String prefixResult) throws FileNotFoundException {
        if (!FileUtility.fileExists(fileName))
            return "";
        TextFileReader reader = new TextFileReader(fileName);
        double reportCounts = StatisticsUtility.calculateMean(reader.readDoubles());
        return prefixResult + " " + reportCounts;
    }

    private String getMeanCountByIgnoringIteration(String fileName, String prefixResult) throws FileNotFoundException {
        if (!FileUtility.fileExists(fileName))
            return "";
        TextFileReader reader = new TextFileReader(fileName);
        double reportCounts = StatisticsUtility.calculateMean(reader.readDoubles(1));
        return prefixResult + " " + reportCounts;
    }

    private String getSigmaCount(String fileName, String prefixResult, int multiplier) throws FileNotFoundException {
        if (!FileUtility.fileExists(fileName))
            return "";
        TextFileReader reader = new TextFileReader(fileName);
        double sigma = StatisticsUtility.calculateStd(reader.readDoubles(1));
        sigma *= multiplier;
        return prefixResult + " " + sigma;
    }
}
