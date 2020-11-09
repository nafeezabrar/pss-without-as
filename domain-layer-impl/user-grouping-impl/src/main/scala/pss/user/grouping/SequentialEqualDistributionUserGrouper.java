package pss.user.grouping;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;

import java.util.*;

public class SequentialEqualDistributionUserGrouper implements UserGroupable {

    @Override
    public UserGroupMapper assignGroup(Set<User> users, Set<PssGroup> pssGroups) {
        Map<User, PssGroup> userToPssGroupMap = new HashMap<>();
        Map<PssGroup, Set<User>> pssGroupToUsersMap = new HashMap<>();
        int totalUsers = users.size();
        int totalDivisions = pssGroups.size();
        int userPerDivision = totalUsers / totalDivisions;

        List<User> orderedUsers = new ArrayList<>();
        orderedUsers.addAll(users);
        orderedUsers.sort((user1, user2) -> Integer.compare(user1.getId(), user2.getId()));

        List<PssGroup> orderedPssGroups = new ArrayList<>();
        orderedPssGroups.addAll(pssGroups);
        orderedPssGroups.sort((p1, p2) -> Integer.compare(p1.getPssGroupId(), p2.getPssGroupId()));

        Iterator<User> userIterator = orderedUsers.iterator();

        for (int i = 0; i < pssGroups.size(); i++) {
            PssGroup pssGroup = orderedPssGroups.get(i);
            Set<User> groupUsers = new HashSet<>();
            for (int j = 0; j < userPerDivision; j++) {
                User user = userIterator.next();
                groupUsers.add(user);
                userToPssGroupMap.put(user, pssGroup);
            }
            pssGroupToUsersMap.put(pssGroup, groupUsers);
        }

        return new UserGroupMapper(userToPssGroupMap, pssGroupToUsersMap);
    }
}
