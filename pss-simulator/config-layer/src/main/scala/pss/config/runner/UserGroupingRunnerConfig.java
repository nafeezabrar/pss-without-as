package pss.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.task.PssGroupingConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.config.task.UserGenerationConfig;
import pss.config.task.UserGroupingConfig;
import pss.data.pss_type.PssType;

import static pss.config.runner.RunnerType.USER_GROUPING_RUNNER;

public class UserGroupingRunnerConfig extends RunnerConfig {
    protected final PssType pssType;
    protected final PssVariableGenerationConfig pssVariableGenerationConfig;
    protected final PssGroupingConfig pssGroupingConfig;
    protected final UserGenerationConfig userGenerationConfig;
    protected final UserGroupingConfig userGroupingConfig;

    public UserGroupingRunnerConfig(PostResultProcessConfig postResultProcessConfig, PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, PssGroupingConfig pssGroupingConfig, UserGenerationConfig userGenerationConfig, UserGroupingConfig userGroupingConfig) {
        super(USER_GROUPING_RUNNER, postResultProcessConfig);
        this.pssType = pssType;
        this.pssVariableGenerationConfig = pssVariableGenerationConfig;
        this.pssGroupingConfig = pssGroupingConfig;
        this.userGenerationConfig = userGenerationConfig;
        this.userGroupingConfig = userGroupingConfig;
    }


    public PssType getPssType() {
        return pssType;
    }

    public PssVariableGenerationConfig getPssVariableGenerationConfig() {
        return pssVariableGenerationConfig;
    }

    public PssGroupingConfig getPssGroupingConfig() {
        return pssGroupingConfig;
    }

    public UserGenerationConfig getUserGenerationConfig() {
        return userGenerationConfig;
    }

    public UserGroupingConfig getUserGroupingConfig() {
        return userGroupingConfig;
    }
}
