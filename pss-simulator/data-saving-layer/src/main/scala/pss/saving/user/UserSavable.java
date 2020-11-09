package pss.saving.user;

import pss.mapper.user.UserMapper;

public interface UserSavable {
    void saveUsers(UserMapper userMapper);
}
