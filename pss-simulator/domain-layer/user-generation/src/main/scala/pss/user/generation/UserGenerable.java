package pss.user.generation;

import pss.exception.PssException;
import pss.mapper.user.UserMapper;

import java.io.IOException;
import java.net.URISyntaxException;

public interface UserGenerable {
    UserMapper generateUsers() throws IOException, PssException, URISyntaxException;
}
