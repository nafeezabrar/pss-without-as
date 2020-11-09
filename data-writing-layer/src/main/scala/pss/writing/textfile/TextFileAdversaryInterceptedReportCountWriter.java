package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.conversion.data_to_result.text.AdversaryInterceptedReportCountToTextResultConverter;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class TextFileAdversaryInterceptedReportCountWriter {
    public static void writeReportCountInTextFile(String fileName, int reportCount, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        AdversaryInterceptedReportCountToTextResultConverter resultConverter = new AdversaryInterceptedReportCountToTextResultConverter();
        TextResult textResult = resultConverter.generateAdversaryReportCountResult(reportCount);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
