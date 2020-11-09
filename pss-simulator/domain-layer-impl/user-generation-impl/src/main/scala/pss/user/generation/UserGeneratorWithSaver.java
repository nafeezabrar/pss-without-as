package pss.user.generation;

import pss.exception.PssException;
import pss.mapper.user.UserMapper;
import pss.saving.user.UserSavable;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserGeneratorWithSaver implements UserGenerable {
    protected final UserGenerable userGenerator;
    protected final UserSavable userSaver;

    public UserGeneratorWithSaver(UserGenerable userGenerator, UserSavable userSaver) {
        this.userGenerator = userGenerator;
        this.userSaver = userSaver;
    }

    @Override
    public UserMapper generateUsers() throws URISyntaxException, PssException, IOException {
        UserMapper userMapper = userGenerator.generateUsers();
        userSaver.saveUsers(userMapper);
        return userMapper;
    }
}
