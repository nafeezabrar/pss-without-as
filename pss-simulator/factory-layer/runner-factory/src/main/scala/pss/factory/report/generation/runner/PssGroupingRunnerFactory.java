package pss.factory.report.generation.runner;

import pss.config.runner.PssGroupingRunnerConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.factory.report.generation.task.OoiGroupMapperFactoryCreator;
import pss.factory.report.generation.task.PssGroupingFactory;
import pss.factory.report.generation.task.PssVariableGeneratorFactory;
import pss.runner.PssGroupingRunner;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

public class PssGroupingRunnerFactory {
    public static PssGroupingRunner generatePssGroupingRunner(PssGroupingRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException {
        PssType pssType = runnerConfig.getPssType();
        PssVariableGenerable pssVariableGenerator = PssVariableGeneratorFactory.generatePssVariableGenerator(pssType, runnerConfig.getPssVariableGenerationConfig(), runnerSeed, resultDirectory);
        PssGroupable pssGrouper = PssGroupingFactory.generatePssGroupable(pssType, runnerConfig.getPssGroupingConfig(), resultDirectory);
        OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory = OoiGroupMapperFactoryCreator.createOoiGroupMapperFactory(runnerConfig.getPssGroupingConfig().isPrintOoiMapper());
        return new PssGroupingRunner(pssType, pssVariableGenerator, pssGrouper, ooiGroupMapperCreatorFactory);
    }
}
