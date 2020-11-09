package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.conversion.data_to_result.text.ObservedReportCountToTextResultConverter;
import pss.data.user.User;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class TextFileReportCountUserMapWriter {
    public static void writeReportCountInTextFile(String fileName, Map<User, Integer> reportCountMap, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        ObservedReportCountToTextResultConverter resultConverter = new ObservedReportCountToTextResultConverter();
        TextResult textResult = resultConverter.generateReportCountResult(reportCountMap);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
