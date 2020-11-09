package pss.variable.grouping;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.result.presentable.printing.PssGroupPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class PssGrouperWithPrinter implements PssGroupable {
    protected final PssGroupable pssGrouper;
    protected final PssGroupPrintable pssGroupPrinter;

    public PssGrouperWithPrinter(PssGroupable pssGrouper, PssGroupPrintable pssGroupPrinter) {
        this.pssGrouper = pssGrouper;
        this.pssGroupPrinter = pssGroupPrinter;
    }

    @Override
    public Set generateGroups(PssType pssType, PssVariables pssVariables) throws InvalidConfigException, PssException, IOException, URISyntaxException {
        Set<PssGroup> pssGroups = pssGrouper.generateGroups(pssType, pssVariables);
        pssGroupPrinter.printPssGroups(pssType, pssGroups);
        return pssGroups;
    }
}
