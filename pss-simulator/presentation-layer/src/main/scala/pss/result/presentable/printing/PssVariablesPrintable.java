package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PssVariablesPrintable {
    void printPssVariables(PssVariables pssVariables, PssType pssType) throws URISyntaxException, IOException, PssException;
}
