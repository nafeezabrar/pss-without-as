package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.writing.html.HtmlPssVariableWriter;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlPssVariablesPrinter implements PssVariablesPrintable {
    protected final String fileName;

    public HtmlPssVariablesPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printPssVariables(PssVariables pssVariables, PssType pssType) throws URISyntaxException, IOException, PssException {
        HtmlPssVariableWriter.writePssVariableInHtml(fileName, pssVariables, pssType);
    }
}
