package pss.user.generation;

import pss.exception.PssException;
import pss.mapper.user.UserMapper;
import pss.result.presentable.printing.UserPrintable;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserGeneratorWithPrinter implements UserGenerable {
    protected final UserGenerable userGenerator;
    protected final UserPrintable userPrinter;

    public UserGeneratorWithPrinter(UserGenerable userGenerator, UserPrintable userPrinter) {
        this.userGenerator = userGenerator;
        this.userPrinter = userPrinter;
    }

    @Override
    public UserMapper generateUsers() throws IOException, PssException, URISyntaxException {
        UserMapper userMapper = userGenerator.generateUsers();
        userPrinter.printUsers(userMapper);
        return userMapper;
    }
}
