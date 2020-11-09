package pss.variable.generation;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.result.presentable.printing.PssVariablesPrintable;

public class PssVariableGeneratorWithPrinter implements PssVariableGenerable {
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssVariablesPrintable pssVariablesPrinter;

    public PssVariableGeneratorWithPrinter(PssVariableGenerable pssVariableGenerator, PssVariablesPrintable pssVariablesPrinter) {
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssVariablesPrinter = pssVariablesPrinter;
    }

    @Override
    public PssVariables generatePssVariables(PssType pssType) throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
        pssVariablesPrinter.printPssVariables(pssVariables, pssType);
        return pssVariables;
    }
}
