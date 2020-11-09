package pss.user.grouping;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface UserGroupable {
    UserGroupMapper assignGroup(Set<User> users, Set<PssGroup> pssGroupSet) throws IOException, PssException, URISyntaxException;
}
