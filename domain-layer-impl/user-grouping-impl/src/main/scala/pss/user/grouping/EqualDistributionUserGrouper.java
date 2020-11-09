package pss.user.grouping;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;

import java.util.*;

public class EqualDistributionUserGrouper implements UserGroupable {

    @Override
    public UserGroupMapper assignGroup(Set<User> users, Set<PssGroup> pssGroups) {
        Map<User, PssGroup> userToPssGroupMap = new HashMap<>();
        Map<PssGroup, Set<User>> pssGroupToUsersMap = new HashMap<>();
        int totalUsers = users.size();
        int totalDivisions = pssGroups.size();
        int userPerDivision = totalUsers / totalDivisions;
        Iterator<User> userIterator = users.iterator();
        for (PssGroup group : pssGroups) {
            Set<User> groupUsers = new HashSet<>();
            for (int i = 0; i < userPerDivision; i++) {
                User user = userIterator.next();
                groupUsers.add(user);
                userToPssGroupMap.put(user, group);
            }
            pssGroupToUsersMap.put(group, groupUsers);
        }
        return new UserGroupMapper(userToPssGroupMap, pssGroupToUsersMap);
    }
}
