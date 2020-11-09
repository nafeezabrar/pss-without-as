package pss.variable.grouping;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.saving.pss_group.PssGroupSavable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class PssGrouperWithSaver implements PssGroupable {
    protected final PssGroupable pssGrouper;
    protected final PssGroupSavable pssGroupSaver;

    public PssGrouperWithSaver(PssGroupable pssGrouper, PssGroupSavable pssGroupSaver) {
        this.pssGrouper = pssGrouper;
        this.pssGroupSaver = pssGroupSaver;
    }

    @Override
    public Set generateGroups(PssType pssType, PssVariables pssVariables) throws InvalidConfigException, PssException, IOException, URISyntaxException {
        Set<PssGroup> pssGroups = pssGrouper.generateGroups(pssType, pssVariables);
        pssGroupSaver.savePssGroups(pssGroups);
        return pssGroups;
    }
}
