package pss.data.mapper.user_group;

import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;

import java.util.Map;
import java.util.Set;

public class UserGroupMapper {
    protected final Map<User, PssGroup> userPssGroupMap;
    protected final Map<PssGroup, Set<User>> userSetMap;

    public UserGroupMapper(Map<User, PssGroup> userToPssGroupMap, Map<PssGroup, Set<User>> pssGroupToUsersMap) {
        this.userPssGroupMap = userToPssGroupMap;
        this.userSetMap = pssGroupToUsersMap;
    }

    public Map<User, PssGroup> getUserPssGroupMap() {
        return userPssGroupMap;
    }

    public Map<PssGroup, Set<User>> getUserSetMap() {
        return userSetMap;
    }

    public int getGroupId(User user) throws PssException {
        if (userPssGroupMap.containsKey(user))
            return userPssGroupMap.get(user).getPssGroupId();
        throw new PssException(String.format("User with id %d and name %s is not registered in any group", user.getId(), user.getName()));
    }

    public PssGroup getPssGroup(User user) throws PssException {
        if (userPssGroupMap.containsKey(user))
            return userPssGroupMap.get(user);
        throw new PssException(String.format("User with id %d and name %s is not registered in any group", user.getId(), user.getName()));
    }
}
