package pss.variable.generation;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;

public interface PssVariableGenerable {
    PssVariables generatePssVariables(PssType pssType) throws Exception;
}
