package pss.saving.pss_variable;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.writing.textfile.TextFilePssVariableWriter;

import java.io.IOException;
import java.net.URISyntaxException;

public class TextFilePssVariableSaver implements PssVariablesSavable{

    protected final String fileName;

    public TextFilePssVariableSaver(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void savePssVariables(PssVariables pssVariables, PssType pssType) throws IOException, URISyntaxException, PssException {
        TextFilePssVariableWriter.writePssVariableInTextFile(fileName, pssVariables, pssType);
    }
}
