package pss.writing.textfile;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.conversion.data_to_result.text.EndPointsDecodabilityToTextResultConverter;
import pss.decodability.EndPointDecodabilityResults;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import static pss.decodability.DecodabilityResultType.DECODABILITY_VALUE;
import static pss.decodability.DecodabilityResultType.DECODED_VALUE_COUNT;

public class TextFileDecodabilityWriter {
    public static void writeDecodabilityInTextFile(String fileName, EndPointDecodabilityResults decodabilityResultMap, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        EndPointsDecodabilityToTextResultConverter resultConverter = new EndPointsDecodabilityToTextResultConverter();
        TextResult textResult = resultConverter.generateResult(decodabilityResultMap, DECODABILITY_VALUE);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }

    public static void writeDecodedValueCountInTextFile(String fileName, EndPointDecodabilityResults decodabilityResultMap, AppendMode appendMode) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter;
        if (appendMode == AppendMode.NOT_APPEND)
            fileWriter = FileUtility.createFileWriteSafely(fileName);
        else
            fileWriter = FileUtility.createFileWriteSafelyInAppendMode(fileName);
        EndPointsDecodabilityToTextResultConverter resultConverter = new EndPointsDecodabilityToTextResultConverter();
        TextResult textResult = resultConverter.generateResult(decodabilityResultMap, DECODED_VALUE_COUNT);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
