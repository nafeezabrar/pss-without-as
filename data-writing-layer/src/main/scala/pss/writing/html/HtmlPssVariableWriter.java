package pss.writing.html;

import pss.conversion.data_to_result.tabular.PssVariableToTabularResultConverter;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlPssVariableWriter {
    public static void writePssVariableInHtml(String fileName, PssVariables pssVariables, PssType pssType) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        PssVariableToTabularResultConverter resultConverter = new PssVariableToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateResult(pssVariables, pssType);
        HtmlTabularResultWriter htmlTabularResultWriter = new HtmlTabularResultWriter(fileWriter);
        htmlTabularResultWriter.writeResult(tabularResult);
    }
}
