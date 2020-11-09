package pss.factory.report.generation.runner;

import pss.config.runner.UserGroupingRunnerConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.task.PssGroupingFactory;
import pss.factory.report.generation.task.PssVariableGeneratorFactory;
import pss.factory.report.generation.task.UserGenerableFactory;
import pss.factory.report.generation.task.UserGroupingFactory;
import pss.runner.UserGroupingRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

public class UserGroupingRunnerFactory {
    public static UserGroupingRunner generateUserGeneratingRunner(UserGroupingRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException {
        PssType pssType = runnerConfig.getPssType();
        PssVariableGenerable pssVariableGenerator = PssVariableGeneratorFactory.generatePssVariableGenerator(pssType, runnerConfig.getPssVariableGenerationConfig(), runnerSeed, resultDirectory);
        PssGroupable pssGrouper = PssGroupingFactory.generatePssGroupable(pssType, runnerConfig.getPssGroupingConfig(), resultDirectory);
        UserGenerable userGenerator = UserGenerableFactory.generateUserGenerator(runnerConfig.getUserGenerationConfig(), runnerSeed, resultDirectory);
        UserGroupable userGrouper = UserGroupingFactory.generateUserGrouper(runnerConfig.getUserGroupingConfig(), resultDirectory);
        return new UserGroupingRunner(pssType, pssVariableGenerator, pssGrouper, userGenerator, userGrouper);
    }
}
