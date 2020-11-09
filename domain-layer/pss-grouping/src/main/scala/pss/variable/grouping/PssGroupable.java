package pss.variable.grouping;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface PssGroupable<TPssGroup extends PssGroup, TPssVariables extends PssVariables> {
    Set<TPssGroup> generateGroups(PssType pssType, TPssVariables pssVariables) throws InvalidConfigException, PssException, IOException, URISyntaxException;
}
