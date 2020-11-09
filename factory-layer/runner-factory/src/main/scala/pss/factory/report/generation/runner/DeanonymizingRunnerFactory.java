package pss.factory.report.generation.runner;

import pss.config.runner.DeanonymizingRunnerConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.FinalReportDirectGeneratorFactory;
import pss.factory.report.generation.cycle_runner.anonymization.DeanonymizationCycleRunnerFactory;
import pss.factory.report.generation.deanonymizer.DeanonymizerSelectorFactory;
import pss.factory.report.generation.deanonymizer.DeanonymizerSetFactory;
import pss.factory.report.generation.task.*;
import pss.report.generation.FinalReportDirectGenerable;
import pss.runner.DeanonymizingRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

public class DeanonymizingRunnerFactory {
    public static DeanonymizingRunner generateDeanonymizingRunner(DeanonymizingRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException {
        PssType pssType = runnerConfig.getPssType();
        PssVariableGenerable pssVariableGenerator = PssVariableGeneratorFactory.generatePssVariableGenerator(pssType, runnerConfig.getPssVariableGenerationConfig(), runnerSeed, resultDirectory);
        PssGroupable pssGrouper = PssGroupingFactory.generatePssGroupable(pssType, runnerConfig.getPssGroupingConfig(), resultDirectory);
        FinalReportDirectGenerable finalReportDirectGenerator = FinalReportDirectGeneratorFactory.generateFinalReportGenerator(runnerConfig.getFinalReportGenerationConfig());
        DeanonymizerSetFactory deanonymizerSetFactory = DeanonymizerFactoryFactory.generateDeanonymizerFactory(pssType, runnerConfig.getDeanonymizationConfig());
        DeanonymizerSelectorFactory deanonymizerSelectorFactory = DeanonymizerChoosableFactoryCreator.generateDeanonymizerChoosableFactory();
        DeanonymizationCycleRunnerFactory deanonymizationCycleRunnerFactory = DeanonymizationCycleRunnerFactoryCreator.createDeanonymizationCycleRunnerFactory();
        UserGenerable userGenerator = UserGenerableFactory.generateUserGenerator(runnerConfig.getUserGenerationConfig(), runnerSeed, resultDirectory);
        UserGroupable userGrouper = UserGroupingFactory.generateUserGrouper(runnerConfig.getUserGroupingConfig(), resultDirectory);

        return new DeanonymizingRunner(pssType, pssVariableGenerator, pssGrouper, userGenerator, userGrouper, finalReportDirectGenerator, deanonymizerSetFactory, deanonymizerSelectorFactory, deanonymizationCycleRunnerFactory);
    }
}
