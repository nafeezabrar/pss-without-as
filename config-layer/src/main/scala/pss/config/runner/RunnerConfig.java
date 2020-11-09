package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;

public abstract class RunnerConfig {
    protected final RunnerType runnerType;
    protected final PostResultProcessConfig postResultProcessConfig;

    public RunnerConfig(RunnerType runnerType, PostResultProcessConfig postResultProcessConfig) {
        this.runnerType = runnerType;
        this.postResultProcessConfig = postResultProcessConfig;
    }

    public RunnerType getRunnerType() {
        return runnerType;
    }

    public PostResultProcessConfig getPostResultProcessConfig() {
        return postResultProcessConfig;
    }
}
