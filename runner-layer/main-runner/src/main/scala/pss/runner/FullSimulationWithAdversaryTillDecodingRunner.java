package pss.runner;

import com.pss.adversary.Adversary;
import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.ValueMap;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.factory.report.generation.FinalReportInheritedGeneratorFactory;
import pss.factory.report.generation.adversary.AdversaryFactory;
import pss.factory.report.generation.adversary.runner.AdversaryRunnerFactory;
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
import pss.post.result.analyzation.fullsimulation.FullSimulationWithAdversaryPostResultAnalyzer;
import pss.report.finalreport.FinalReport;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.generation.ObservedReportGenerable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.full_cycle.FullCycleResult;
import pss.runner.cycle.fullcycle.FullCycleRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FullSimulationWithAdversaryTillDecodingRunner implements PssRunnable {
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
    protected final ObservedReportGenerable observedReportGenerator;
    protected final AnonymizableReportGenerable anonymizableReportGenerator;
    protected final FinalReportInheritedGeneratorFactory finalReportGeneratorFactory;
    protected final AnonymityGenerable anonymityGenerator;
    protected final FullCycleRunnerFactory fullCycleRunnerFactory;
    protected final AdversaryFactory adversaryFactory;
    protected final AdversaryRunnerFactory adversaryRunnerFactory;
    protected final FullSimulationPostResultAnalyzer postResultAnalyzer;
    protected final FullSimulationWithAdversaryPostResultAnalyzer postResultAnalyzerWithAdversary;

    public FullSimulationWithAdversaryTillDecodingRunner(PssType pssType, PssVariableGenerable pssVariableGenerator, PssGroupable pssGrouper, UserGenerable userGenerator, UserGroupable userGrouper, OoiUserGroupMapperFactory ooiUserGroupMapperFactory, AnonymizerFactory anonymizerFactory, DeanonymizerSetFactory deanonymizerSetFactory, OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory, AnonymizerSelectorFactory anonymizerSelectorFactory, DeanonymizerSelectorFactory deanonymizerSelectorFactory, ObservedReportGenerable observedReportGenerator, AnonymizableReportGenerable anonymizableReportGenerator, FinalReportInheritedGeneratorFactory finalReportGeneratorFactory, AnonymityGenerable anonymityGenerator, FullCycleRunnerFactory fullCycleRunnerFactory, AdversaryFactory adversaryFactory, AdversaryRunnerFactory adversaryRunnerFactory, FullSimulationPostResultAnalyzer postResultAnalyzer1, FullSimulationWithAdversaryPostResultAnalyzer postResultAnalyzer) {
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
        this.adversaryFactory = adversaryFactory;
        this.adversaryRunnerFactory = adversaryRunnerFactory;
        this.postResultAnalyzer = postResultAnalyzer1;
        this.postResultAnalyzerWithAdversary = postResultAnalyzer;
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

        AnonymizerGroupMapper anonymizerGroupMapper = anonymizerFactory.createAnonymizer(pssGroups);
        AnonymizerSelector anonymizerSelector = anonymizerSelectorFactory.generateAnonymizerChooser(userGroupMapper, anonymizerGroupMapper);
        DeanonymizerGroupMapper deanonymizerGroupMapper = deanonymizerSetFactory.createDeanonymizer(pssGroups);
        DeanonymizerSelector deanonymizerSelector = deanonymizerSelectorFactory.generateDeanonymizerChooser(userGroupMapper, deanonymizerGroupMapper);

        FullCycleRunner fullCycleRunner = fullCycleRunnerFactory.generateCycleRunner(pssType, pssVariables, ooiUserGroupMapper, anonymizerSelector, userMapper, anonymityGenerator, anonymizableReportGenerator, finalReportGeneratorFactory, deanonymizerGroupMapper, deanonymizerSelector, pssGroups);
        List<ObservedReport> observedReports = new ArrayList<>();
        List<FullCycleResult> fullCycleResults = new ArrayList<>();
        List<FinalReport> finalReports = new ArrayList<>();
        boolean fullyDecoded = false;
        ValueMap valueMap = pssVariables.getValueMap();
        int totalValues = pssVariables.getValueMap().getValues().size();
        int reportId = 1;
        while (!fullyDecoded) {
            ObservedReport observedReport = observedReportGenerator.generateObservedReport(reportId, valueMap, ooiUserGroupMapper);
            reportId++;
            observedReports.add(observedReport);
            FullCycleResult fullCycleResult = fullCycleRunner.runCycle(observedReport);
            fullCycleResults.add(fullCycleResult);
            finalReports.add(fullCycleResult.getFinalReport());
            int totalDecoded = fullCycleResult.getTotalDecoded();
            if (totalDecoded == totalValues)
                fullyDecoded = true;
        }
        System.out.println("pss run completed");
        postResultAnalyzer.analyzeResult(pssVariables, pssGroups, userMapper.getUsers(), observedReports, ooiUserGroupMapper, fullCycleResults);
        Adversary adversary = adversaryFactory.createAdversary(finalReports, userMapper.getUsers());
        System.out.println("adversary created");
        AdversaryRunnable adversaryRunner = adversaryRunnerFactory.createAdversaryRunner(adversary, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper, pssGroups, finalReports);
        System.out.println("adversary runner created");
        AdversaryResult adversaryResult = adversaryRunner.runAdversary();

        postResultAnalyzerWithAdversary.analyzeResult(adversary, observedReports, ooiUserGroupMapper, adversaryResult, pssVariables);
    }
}
