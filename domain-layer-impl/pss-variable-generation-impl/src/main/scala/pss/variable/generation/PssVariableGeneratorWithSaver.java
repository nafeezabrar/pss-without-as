package pss.variable.generation;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.saving.pss_variable.PssVariablesSavable;

public class PssVariableGeneratorWithSaver implements PssVariableGenerable {
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssVariablesSavable pssVariablesSaver;

    public PssVariableGeneratorWithSaver(PssVariableGenerable pssVariableGenerator, PssVariablesSavable pssVariablesSaver) {
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssVariablesSaver = pssVariablesSaver;
    }

    @Override
    public PssVariables generatePssVariables(PssType pssType) throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
        pssVariablesSaver.savePssVariables(pssVariables, pssType);
        return pssVariables;
    }
}
