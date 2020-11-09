package pss.runner;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.variable.generation.PssVariableGenerable;

public class PssVariableGeneratingRunner implements PssRunnable {
    protected final PssType pssType;
    protected final PssVariableGenerable pssVariableGenerator;

    public PssVariableGeneratingRunner(PssType pssType, PssVariableGenerable pssVariableGenerator) {
        this.pssType = pssType;
        this.pssVariableGenerator = pssVariableGenerator;
    }

    @Override
    public void run() throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
    }
}
