package pss.runner;

import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.factory.report.generation.FinalReportInheritedGeneratorFactory;
import pss.factory.report.generation.anonymizer.AnonymizerFactory;
import pss.factory.report.generation.anonymizer.AnonymizerSelectorFactory;
import pss.factory.report.generation.cycle_runner.anonymization.FullCycleRunnerFactory;
import pss.factory.report.generation.deanonymizer.DeanonymizerSelectorFactory;
import pss.factory.report.generation.deanonymizer.DeanonymizerSetFactory;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.factory.report.generation.mapper.OoiUserGroupMapperFactory;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_group.OoiGroupMapperCreator;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.post.result.analyzation.fullsimulation.FullSimulationPostResultAnalyzer;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.generation.ObservedReportSetGenerable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.runner.cycle.fullcycle.FullCycleRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import java.util.List;
import java.util.Set;

public class FullSimulationRunner implements PssRunnable {
    protected final PssType pssType;
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssGroupable pssGrouper;
    protected final UserGenerable userGenerator;
    protected final UserGroupable userGrouper;
    protected final OoiUserGroupMapperFactory ooiUserGroupMapperFactory;
    protected final AnonymizerFactory anonymizerFactory;
    protected final DeanonymizerSetFactory deanonymizerSetFactory;
    protected final OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory;
    protected final AnonymizerSelectorFactory anonymizerSelectorFactory;
    protected final DeanonymizerSelectorFactory deanonymizerSelectorFactory;
    protected final ObservedReportSetGenerable observedReportGenerator;
    protected final AnonymizableReportGenerable anonymizableReportGenerator;
    protected final FinalReportInheritedGeneratorFactory finalReportGeneratorFactory;
    protected final AnonymityGenerable anonymityGenerator;
    protected final FullCycleRunnerFactory fullCycleRunnerFactory;
    protected final FullSimulationPostResultAnalyzer postResultAnalyzer;

    public FullSimulationRunner(PssType pssType, PssVariableGenerable pssVariableGenerator, PssGroupable pssGrouper, UserGenerable userGenerator, UserGroupable userGrouper, OoiUserGroupMapperFactory ooiUserGroupMapperFactory, AnonymizerFactory anonymizerFactory, DeanonymizerSetFactory deanonymizerSetFactory, OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory, AnonymizerSelectorFactory anonymizerSelectorFactory, DeanonymizerSelectorFactory deanonymizerSelectorFactory, ObservedReportSetGenerable observedReportGenerator, AnonymizableReportGenerable anonymizableReportGenerator, FinalReportInheritedGeneratorFactory finalReportGeneratorFactory, AnonymityGenerable anonymityGenerator, FullCycleRunnerFactory fullCycleRunnerFactory, FullSimulationPostResultAnalyzer postResultAnalyzer) {
        this.pssType = pssType;
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssGrouper = pssGrouper;
        this.userGenerator = userGenerator;
        this.userGrouper = userGrouper;
        this.ooiUserGroupMapperFactory = ooiUserGroupMapperFactory;
        this.anonymizerFactory = anonymizerFactory;
        this.deanonymizerSetFactory = deanonymizerSetFactory;
        this.ooiGroupMapperCreatorFactory = ooiGroupMapperCreatorFactory;
        this.anonymizerSelectorFactory = anonymizerSelectorFactory;
        this.deanonymizerSelectorFactory = deanonymizerSelectorFactory;
        this.observedReportGenerator = observedReportGenerator;
        this.anonymizableReportGenerator = anonymizableReportGenerator;
        this.finalReportGeneratorFactory = finalReportGeneratorFactory;
        this.anonymityGenerator = anonymityGenerator;
        this.fullCycleRunnerFactory = fullCycleRunnerFactory;
        this.postResultAnalyzer = postResultAnalyzer;
    }

    @Override
    public void run() throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
        Set<PssGroup> pssGroups = pssGrouper.generateGroups(pssType, pssVariables);
        OoiGroupMapperCreator ooiGroupMapperCreator = ooiGroupMapperCreatorFactory.generateOoiGroupMapperCreator();
        OoiGroupMapper ooiGroupMapper = ooiGroupMapperCreator.generateOoiGroupMapper(pssType, pssGroups, pssVariables);
        UserMapper userMapper = userGenerator.generateUsers();
        UserGroupMapper userGroupMapper = userGrouper.assignGroup(userMapper.getUsers(), pssGroups);
        OoiUserGroupMappable ooiUserGroupMapper = ooiUserGroupMapperFactory.createOoiUserGroupMapper(pssType, pssGroups, userMapper, ooiGroupMapper, userGroupMapper);
        List<ObservedReport> observedReports = observedReportGenerator.generateObservedReports(pssVariables.getValueMap(), ooiUserGroupMapper);

        AnonymizerGroupMapper anonymizerGroupMapper = anonymizerFactory.createAnonymizer(pssGroups);
        AnonymizerSelector anonymizerSelector = anonymizerSelectorFactory.generateAnonymizerChooser(userGroupMapper, anonymizerGroupMapper);
        DeanonymizerGroupMapper deanonymizerGroupMapper = deanonymizerSetFactory.createDeanonymizer(pssGroups);
        DeanonymizerSelector deanonymizerSelector = deanonymizerSelectorFactory.generateDeanonymizerChooser(userGroupMapper, deanonymizerGroupMapper);

        FullCycleRunner fullCycleRunner = fullCycleRunnerFactory.generateCycleRunner(pssType, pssVariables, ooiUserGroupMapper, anonymizerSelector, userMapper, anonymityGenerator, anonymizableReportGenerator, finalReportGeneratorFactory, deanonymizerGroupMapper, deanonymizerSelector, pssGroups);

        List<FullCycleResult> fullCycleResults = fullCycleRunner.runCycle(observedReports);
        FullCycleResult fullCycleResult = fullCycleResults.get(fullCycleResults.size() - 1);
        int totalDecoded = fullCycleResult.getTotalDecoded();
        if (totalDecoded == 0)
            System.out.println(totalDecoded);


        postResultAnalyzer.analyzeResult(pssVariables, pssGroups, userMapper.getUsers(), observedReports, ooiUserGroupMapper, fullCycleResults);
    }
}
