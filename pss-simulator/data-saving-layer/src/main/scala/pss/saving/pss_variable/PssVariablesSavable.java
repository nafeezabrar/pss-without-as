package pss.saving.pss_variable;


import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PssVariablesSavable {
    void savePssVariables(PssVariables pssVariables, PssType pssType) throws IOException, URISyntaxException, PssException;
}
