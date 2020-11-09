package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.conversion.data_to_result.text.AdversaryOwnReportCountToTextResultConverter;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class TextFileAdversaryOwnReportedValueCountWriter {
    public static void writeReportedValueCountInTextFile(String fileName, int reportedValueCount, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        AdversaryOwnReportCountToTextResultConverter resultConverter = new AdversaryOwnReportCountToTextResultConverter();
        TextResult textResult = resultConverter.generateObservedReportResult(reportedValueCount);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
