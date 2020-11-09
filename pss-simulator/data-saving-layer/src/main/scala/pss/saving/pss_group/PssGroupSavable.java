package pss.saving.pss_group;


import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface PssGroupSavable {
    void savePssGroups(Set<PssGroup> pssGroups) throws IOException, URISyntaxException, PssException;
}
