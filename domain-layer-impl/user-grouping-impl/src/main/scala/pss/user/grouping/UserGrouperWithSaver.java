package pss.user.grouping;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.saving.user_group_mapper.UserGroupSavable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class UserGrouperWithSaver implements UserGroupable {
    protected final UserGroupable userGrouper;
    protected final UserGroupSavable userGroupSaver;

    public UserGrouperWithSaver(UserGroupable userGrouper, UserGroupSavable userGroupSaver) {
        this.userGrouper = userGrouper;
        this.userGroupSaver = userGroupSaver;
    }

    @Override
    public UserGroupMapper assignGroup(Set<User> users, Set<PssGroup> pssGroups) throws URISyntaxException, PssException, IOException {
        UserGroupMapper userGroupMapper = userGrouper.assignGroup(users, pssGroups);
        userGroupSaver.save(userGroupMapper);
        return userGroupMapper;
    }
}
