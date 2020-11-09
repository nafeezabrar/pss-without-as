package pss.result.presentable.printing;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface UserGroupPrintable {
    void printUserGroupMapper(UserGroupMapper userGroupMapper) throws URISyntaxException, PssException, IOException;
}
