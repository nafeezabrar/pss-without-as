package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.mapper.user.UserMapper;

import java.io.IOException;
import java.net.URISyntaxException;

public interface UserPrintable {
    void printUsers(UserMapper userMapper) throws URISyntaxException, PssException, IOException;
}
