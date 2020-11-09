package pss.factory.report.generation.mapper;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pss_type.PssType.PssDimensionType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.mapping.ooi_user_group.SimpleMultiOoiUserGroupMapper;
import pss.mapping.ooi_user_group.SimpleSingleOoiUserGroupMapper;

import java.util.Set;


public class OoiUserGroupMapperFactory {
    public OoiUserGroupMappable createOoiUserGroupMapper(PssType pssType, Set<PssGroup> pssGroups, UserMapper userMapper, OoiGroupMapper ooiGroupMapper, UserGroupMapper userGroupMapper) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SimpleSingleOoiUserGroupMapper(pssGroups, userMapper, ooiGroupMapper, userGroupMapper);
            case MULTI:
                return new SimpleMultiOoiUserGroupMapper(pssGroups, userMapper, ooiGroupMapper, userGroupMapper);
        }
        throw new PssException(String.format("Ooi User Group mapper factory not implemented for %s", pssDimensionType));
    }
}
