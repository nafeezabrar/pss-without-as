package pss.factory.report.generation.runner;

import pss.anonymity.generation.AnonymityGenerable;
import pss.config.adversary.MultipleApsAdversaryConfig;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.runner.FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.FinalReportInheritedGeneratorFactory;
import pss.factory.report.generation.FinalReportInheritedGeneratorFactoryCreator;
import pss.factory.report.generation.adversary.MultipleAdversaryFactory;
import pss.factory.report.generation.adversary.MultipleApsAdversaryFactoryCreator;
import pss.factory.report.generation.adversary.runner.MultipleApsAdversaryRunnerFactory;
import pss.factory.report.generation.anonymizer.AnonymizerFactory;
import pss.factory.report.generation.anonymizer.AnonymizerSelectorFactory;
import pss.factory.report.generation.cycle_runner.anonymization.FullCycleRunnerFactory;
import pss.factory.report.generation.cycle_runner.anonymization.impl.FullCycleRunnerFactoryImpl;
import pss.factory.report.generation.deanonymizer.DeanonymizerSelectorFactory;
import pss.factory.report.generation.deanonymizer.DeanonymizerSetFactory;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.factory.report.generation.mapper.OoiUserGroupMapperFactory;
import pss.factory.report.generation.post.result.analyzer.FullSimulationPostResultAnalyzerFactory;
import pss.factory.report.generation.post.result.analyzer.FullSimulationWithAdversaryPostResultAnalyzerFactory;
import pss.factory.report.generation.task.*;
import pss.post.result.analyzation.fullsimulation.FullSimulationPostResultAnalyzer;
import pss.post.result.analyzation.fullsimulation.FullSimulationWithAdversaryPostResultAnalyzer;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.generation.ObservedReportGenerable;
import pss.runner.FullSimulationWithGeneratedAdversaryTillDecodingRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import static pss.factory.report.generation.task.DeanonymizerFactoryFactory.generateDeanonymizerFactory;
import static pss.factory.report.generation.task.DeanonymizerSelectorFactoryCreator.generateDeanonymizerChoosableFactory;
import static pss.factory.report.generation.task.OoiGroupMapperFactoryCreator.createOoiGroupMapperFactory;
import static pss.factory.report.generation.task.UserGenerableFactory.generateUserGenerator;
import static pss.factory.report.generation.task.UserGroupingFactory.generateUserGrouper;

public class FullSimulationWithGeneratedAdversaryTillDecodingRunnerFactory {
    public static FullSimulationWithGeneratedAdversaryTillDecodingRunner generateFullSimulationWithGeneratedAdversaryTillDecodingRunner(FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig runnerConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException, ReaderException {
        PssType pssType = runnerConfig.getPssType();
        PssVariableGenerable pssVariableGenerator = PssVariableGeneratorFactory.generatePssVariableGenerator(pssType, runnerConfig.getPssVariableGenerationConfig(), runnerSeed, resultDirectory);
        PssGroupable pssGrouper = PssGroupingFactory.generatePssGroupable(pssType, runnerConfig.getPssGroupingConfig(), resultDirectory);
        OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory = createOoiGroupMapperFactory(runnerConfig.getPssGroupingConfig().isPrintOoiMapper());
        UserGenerable userGenerator = generateUserGenerator(runnerConfig.getUserGenerationConfig(), runnerSeed, resultDirectory);
        UserGroupable userGrouper = generateUserGrouper(runnerConfig.getUserGroupingConfig(), resultDirectory);
        OoiUserGroupMapperFactory ooiUserGroupMapperFactory = OoiUserGroupMapperFactoryCreator.createOoiUserGroupMapperFactory();
        ObservedReportGenerable observedReportGenerator = ObservedReportGeneratorFactory.generateObservedReportGenerator(pssType, runnerConfig.getObservedReportGenerationConfig(), runnerSeed, resultDirectory);
        AnonymizerFactory anonymizerFactory = AnonymizerFactoryFactory.generateAnonymizerFactory(pssType, runnerSeed, runnerConfig.getAnonymizationConfig());
        AnonymizerSelectorFactory anonymizerSelectorFactory = AnonymizerSelectorFactoryCreator.generateAnonymizerChoosableFactory();
        AnonymizableReportGenerable anonymizableReportGenerator = AnonymizableReportGenerableFactory.generateAnonymizableReportGenerator(pssType, runnerConfig.getAnonymizationConfig().getAnonymizationMethod());
        AnonymityGenerable anonymityGenerator = AnonymityGeneratorFactory.generateAnoymityGenerator(runnerConfig.getAnonymityGenerationConfig(), runnerSeed);
        DeanonymizerSetFactory deanonymizerSetFactory = generateDeanonymizerFactory(pssType, runnerConfig.getDeanonymizationConfig());
        DeanonymizerSelectorFactory deanonymizerSelectorFactory = generateDeanonymizerChoosableFactory();

        FinalReportInheritedGeneratorFactory finalReportInheritedFactory = FinalReportInheritedGeneratorFactoryCreator.generateFinalReportInheritedFactory(pssType, runnerConfig.getFinalReportGenerationConfig());
        FullCycleRunnerFactory fullCycleRunnerFactory = new FullCycleRunnerFactoryImpl(runnerSeed, runnerConfig.getCycleRunnerConfig(), resultDirectory);

        MultipleApsAdversaryConfig adversaryConfig = runnerConfig.getAdversaryConfig();
        MultipleAdversaryFactory adversaryFactory = MultipleApsAdversaryFactoryCreator.createAdversaryFactory(pssType, adversaryConfig, runnerSeed);
        MultipleApsAdversaryRunnerFactory adversaryRunnerFactory = new MultipleApsAdversaryRunnerFactory(adversaryConfig, resultDirectory);
        FullSimulationWithAdversaryPostResultAnalyzer postResultAnalyzerWithAdversary = FullSimulationWithAdversaryPostResultAnalyzerFactory.createResultAnalyzer(runnerConfig.getPostResultProcessConfig(), resultDirectory);
        FullSimulationPostResultAnalyzer postResultAnalyzer = FullSimulationPostResultAnalyzerFactory.createResultAnalyzer(runnerConfig.getPostResultProcessConfig(), resultDirectory);
        ReportFilteringConfig reportFilteringConfig = adversaryConfig.getReportFilteringConfig();
        return new FullSimulationWithGeneratedAdversaryTillDecodingRunner(pssType, pssVariableGenerator, pssGrouper, userGenerator, userGrouper, ooiUserGroupMapperFactory, anonymizerFactory, deanonymizerSetFactory, ooiGroupMapperCreatorFactory, anonymizerSelectorFactory, deanonymizerSelectorFactory, observedReportGenerator, anonymizableReportGenerator, finalReportInheritedFactory, anonymityGenerator, fullCycleRunnerFactory, adversaryFactory, adversaryRunnerFactory, postResultAnalyzer, postResultAnalyzerWithAdversary, reportFilteringConfig);
    }
}
