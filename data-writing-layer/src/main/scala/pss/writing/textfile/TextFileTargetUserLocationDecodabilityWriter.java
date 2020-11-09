package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.conversion.data_to_result.TargetUserLocationDecodabilityToResultConvertable;
import pss.conversion.data_to_result.text.AdversaryInterceptedReportCountToTextResultConverter;
import pss.conversion.data_to_result.text.TargetUserLocationDecodabilityToTextResultConverter;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class TextFileTargetUserLocationDecodabilityWriter {
    public static void writeTargetUserLocationDecodability(String fileName, double locationDecodability, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        TargetUserLocationDecodabilityToTextResultConverter resultConverter = new TargetUserLocationDecodabilityToTextResultConverter();
        TextResult textResult = resultConverter.generateTargetUserLocationDecodabilityResult(locationDecodability);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
