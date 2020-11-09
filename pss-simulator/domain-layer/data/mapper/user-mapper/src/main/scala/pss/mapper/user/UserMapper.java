package pss.mapper.user;

import pss.data.user.User;
import pss.exception.PssException;

import java.util.Map;
import java.util.Set;

public class UserMapper {
    protected final Set<User> users;
    protected final Map<Integer, User> idUserMap;
    protected final Map<User, Integer> userIdMap;

    public UserMapper(Set<User> users, Map<User, Integer> userIdMap, Map<Integer, User> idUserMap) {
        this.users = users;
        this.idUserMap = idUserMap;
        this.userIdMap = userIdMap;
    }


    public User getUser(int userId) throws PssException {
        if (idUserMap.containsKey(userId))
            return this.idUserMap.get(userId);
        throw new PssException(String.format("User with id %d not found", userId));
    }

    public int getUserId(User user) throws PssException {
        if (userIdMap.containsKey(user))
            return userIdMap.get(user);
        throw new PssException(String.format("Id of User with name %s not found", user.getName()));
    }

    public Set<User> getUsers() {
        return users;
    }
}
