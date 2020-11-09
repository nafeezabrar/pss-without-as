package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;

import static pss.config.runner.RunnerType.RESULT_FILTERING_RUNNER;

public class ResultFilteringRunnerConfig extends RunnerConfig {

    protected final ResultFilteringType resultFilteringType;
    protected final String fileName;

    public ResultFilteringRunnerConfig(PostResultProcessConfig postResultProcessConfig, ResultFilteringType resultFilteringType, String fileName) {
        super(RESULT_FILTERING_RUNNER, postResultProcessConfig);
        this.resultFilteringType = resultFilteringType;
        this.fileName = fileName;
    }


    public ResultFilteringType getResultFilteringType() {
        return resultFilteringType;
    }

    public String getFileName() {
        return fileName;
    }

    public enum ResultFilteringType {
        CALCULATE_MEAN_STD,
        CALCULATE_MEAN
    }
}
