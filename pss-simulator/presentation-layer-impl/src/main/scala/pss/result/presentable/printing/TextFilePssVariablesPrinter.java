package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.writing.textfile.TextFilePssVariableWriter;

import java.io.IOException;
import java.net.URISyntaxException;

public class TextFilePssVariablesPrinter implements PssVariablesPrintable{

    protected final String fileName;

    public TextFilePssVariablesPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printPssVariables(PssVariables pssVariables, PssType pssType) throws URISyntaxException, PssException, IOException {
        TextFilePssVariableWriter.writePssVariableInTextFile(fileName, pssVariables, pssType);
    }
}
