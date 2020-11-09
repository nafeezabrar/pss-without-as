package pss.factory.report.generation.runner;

import pss.config.runner.UserGeneratingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.task.UserGenerableFactory;
import pss.runner.UserGeneratingRunner;
import pss.user.generation.UserGenerable;

public class UserGeneratingRunnerFactory {
    public static UserGeneratingRunner generateUserGeneratingRunner(UserGeneratingRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, PssException, InvalidConfigException {
        UserGenerable userGenerator = UserGenerableFactory.generateUserGenerator(runnerConfig.getUserGenerationConfig(), runnerSeed, resultDirectory);
        return new UserGeneratingRunner(userGenerator);
    }
}
