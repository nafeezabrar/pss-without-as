package pss.mapping.ooi_group;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface OoiGroupMapperCreator {
    OoiGroupMapper generateOoiGroupMapper(PssType pssType, Set<PssGroup> pssGroups, PssVariables pssVariables) throws PssException, IOException, URISyntaxException;
}
