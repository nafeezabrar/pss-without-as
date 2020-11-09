package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface PssGroupPrintable {
    void printPssGroups(PssType pssType, Set<PssGroup> pssGroups) throws URISyntaxException, IOException, PssException;
}
