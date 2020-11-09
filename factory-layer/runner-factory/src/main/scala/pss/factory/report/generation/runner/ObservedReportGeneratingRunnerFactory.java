package pss.factory.report.generation.runner;

import pss.config.runner.ObservedReportGeneratingRunnerConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.factory.report.generation.mapper.OoiUserGroupMapperFactory;
import pss.factory.report.generation.task.*;
import pss.report.generation.ObservedReportSetGenerable;
import pss.runner.ObservedReportGeneratingRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import static pss.factory.report.generation.task.OoiGroupMapperFactoryCreator.createOoiGroupMapperFactory;

public class ObservedReportGeneratingRunnerFactory {
    public static ObservedReportGeneratingRunner generateObservedReportGeneratingRunner(ObservedReportGeneratingRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException, ReaderException {
        PssType pssType = runnerConfig.getPssType();
        PssVariableGenerable pssVariableGenerator = PssVariableGeneratorFactory.generatePssVariableGenerator(pssType, runnerConfig.getPssVariableGenerationConfig(), runnerSeed, resultDirectory);
        PssGroupable pssGrouper = PssGroupingFactory.generatePssGroupable(pssType, runnerConfig.getPssGroupingConfig(), resultDirectory);
        OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory = createOoiGroupMapperFactory(runnerConfig.getPssGroupingConfig().isPrintOoiMapper());
        UserGenerable userGenerator = UserGenerableFactory.generateUserGenerator(runnerConfig.getUserGenerationConfig(), runnerSeed, resultDirectory);
        UserGroupable userGrouper = UserGroupingFactory.generateUserGrouper(runnerConfig.getUserGroupingConfig(), resultDirectory);
        OoiUserGroupMapperFactory ooiUserGroupMapperFactory = OoiUserGroupMapperFactoryCreator.createOoiUserGroupMapperFactory();
        ObservedReportSetGenerable observedReportGenerator = ObservedReportSetGeneratorFactory.generateObservedReportGenerator(pssType, runnerConfig.getObservedReportGenerationConfig(), runnerSeed, resultDirectory);
        return new ObservedReportGeneratingRunner(pssType, pssVariableGenerator, pssGrouper, ooiGroupMapperCreatorFactory, userGenerator, userGrouper, ooiUserGroupMapperFactory, observedReportGenerator);
    }
}
