package pss.writing.textfile;

import pss.conversion.data_to_result.text.PssVariableToTextResultConverter;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.text.TextResult;
import pss.writing.result.text.TextFileTextResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class TextFilePssVariableWriter {
    public static void writePssVariableInTextFile(String fileName, PssVariables pssVariables, PssType pssType) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        PssVariableToTextResultConverter resultConverter = new PssVariableToTextResultConverter();
        TextResult textResult = resultConverter.generateResult(pssVariables, pssType);
        TextFileTextResultWriter resultWriter = new TextFileTextResultWriter(fileWriter);
        resultWriter.writeResult(textResult);
    }
}
