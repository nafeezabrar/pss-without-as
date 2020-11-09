package pss.runner;

import pss.exception.PssException;
import pss.user.generation.UserGenerable;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserGeneratingRunner implements PssRunnable {
    protected final UserGenerable userGenerator;

    public UserGeneratingRunner(UserGenerable userGenerator) {
        this.userGenerator = userGenerator;
    }

    @Override
    public void run() throws URISyntaxException, PssException, IOException {
        userGenerator.generateUsers();
    }
}
