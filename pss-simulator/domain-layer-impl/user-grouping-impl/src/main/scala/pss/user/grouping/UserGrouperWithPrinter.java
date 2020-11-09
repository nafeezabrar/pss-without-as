package pss.user.grouping;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.result.presentable.printing.UserGroupPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class UserGrouperWithPrinter implements UserGroupable {
    protected final UserGroupable userGrouper;
    protected final UserGroupPrintable userGroupPrinter;

    public UserGrouperWithPrinter(UserGroupable userGrouper, UserGroupPrintable userGroupPrinter) {
        this.userGrouper = userGrouper;
        this.userGroupPrinter = userGroupPrinter;
    }

    @Override
    public UserGroupMapper assignGroup(Set<User> users, Set<PssGroup> pssGroupSet) throws IOException, PssException, URISyntaxException {
        UserGroupMapper userGroupMapper = userGrouper.assignGroup(users, pssGroupSet);
        userGroupPrinter.printUserGroupMapper(userGroupMapper);
        return userGroupMapper;
    }
}
