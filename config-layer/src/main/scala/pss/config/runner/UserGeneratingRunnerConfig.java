package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.UserGenerationConfig;

import static pss.config.runner.RunnerType.USER_GENERATING_RUNNER;

public class UserGeneratingRunnerConfig extends RunnerConfig {

    protected final UserGenerationConfig userGenerationConfig;

    public UserGeneratingRunnerConfig(PostResultProcessConfig postResultProcessConfig, UserGenerationConfig userGenerationConfig) {
        super(USER_GENERATING_RUNNER, postResultProcessConfig);
        this.userGenerationConfig = userGenerationConfig;
    }


    public UserGenerationConfig getUserGenerationConfig() {
        return userGenerationConfig;
    }
}
