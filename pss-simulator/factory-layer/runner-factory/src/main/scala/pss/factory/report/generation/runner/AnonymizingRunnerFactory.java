package pss.factory.report.generation.runner;

import pss.anonymity.generation.AnonymityGenerable;
import pss.config.runner.AnonymizingRunnerConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.anonymizer.AnonymizerFactory;
import pss.factory.report.generation.anonymizer.AnonymizerSelectorFactory;
import pss.factory.report.generation.cycle_runner.anonymization.AnonymizationCycleRunnerFactory;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.factory.report.generation.mapper.OoiUserGroupMapperFactory;
import pss.factory.report.generation.task.*;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.generation.ObservedReportSetGenerable;
import pss.runner.AnonymizingRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import static pss.factory.report.generation.task.ObservedReportSetGeneratorFactory.generateObservedReportGenerator;
import static pss.factory.report.generation.task.OoiGroupMapperFactoryCreator.createOoiGroupMapperFactory;

public class AnonymizingRunnerFactory {
    public static AnonymizingRunner generateAnonymizingRunner(AnonymizingRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException, ReaderException {
        PssType pssType = runnerConfig.getPssType();
        PssVariableGenerable pssVariableGenerator = PssVariableGeneratorFactory.generatePssVariableGenerator(pssType, runnerConfig.getPssVariableGenerationConfig(), runnerSeed, resultDirectory);
        PssGroupable pssGrouper = PssGroupingFactory.generatePssGroupable(pssType, runnerConfig.getPssGroupingConfig(), resultDirectory);
        OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory = createOoiGroupMapperFactory(runnerConfig.getPssGroupingConfig().isPrintOoiMapper());
        UserGenerable userGenerator = UserGenerableFactory.generateUserGenerator(runnerConfig.getUserGenerationConfig(), runnerSeed, resultDirectory);
        UserGroupable userGrouper = UserGroupingFactory.generateUserGrouper(runnerConfig.getUserGroupingConfig(), resultDirectory);
        OoiUserGroupMapperFactory ooiUserGroupMapperFactory = OoiUserGroupMapperFactoryCreator.createOoiUserGroupMapperFactory();
        ObservedReportSetGenerable observedReportGenerator = generateObservedReportGenerator(pssType, runnerConfig.getObservedReportGenerationConfig(), runnerSeed, resultDirectory);
        AnonymizerFactory anonymizerFactory = AnonymizerFactoryFactory.generateAnonymizerFactory(pssType, runnerSeed, runnerConfig.getAnonymizationConfig());
        AnonymizerSelectorFactory anonymizerSelectorFactory = AnonymizerSelectorFactoryCreator.generateAnonymizerChoosableFactory();
        AnonymizableReportGenerable anonymizableReportGenerator = AnonymizableReportGenerableFactory.generateAnonymizableReportGenerator(pssType, runnerConfig.getAnonymizationConfig().getAnonymizationMethod());
        AnonymityGenerable anonymityGenerator = AnonymityGeneratorFactory.generateAnoymityGenerator(runnerConfig.getAnonymityGenerationConfig(), runnerSeed);
        AnonymizationCycleRunnerFactory anonymizationCycleRunnerFactory = AnonymizationCycleRunnerFactoryCreator.createAnonymizationCycleRunnerFactory(runnerConfig.getCycleRunnerConfig(), resultDirectory);

        return new AnonymizingRunner(pssType, pssVariableGenerator, pssGrouper, userGenerator, userGrouper, ooiUserGroupMapperFactory, anonymizerFactory, ooiGroupMapperCreatorFactory, anonymizerSelectorFactory, observedReportGenerator, anonymizableReportGenerator, anonymityGenerator, anonymizationCycleRunnerFactory);
    }
}
