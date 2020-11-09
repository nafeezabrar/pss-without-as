package pss.runner;

import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.factory.report.generation.anonymizer.AnonymizerFactory;
import pss.factory.report.generation.anonymizer.AnonymizerSelectorFactory;
import pss.factory.report.generation.cycle_runner.anonymization.AnonymizationCycleRunnerFactory;
import pss.factory.report.generation.mapper.OoiGroupMapperCreatorFactory;
import pss.factory.report.generation.mapper.OoiUserGroupMapperFactory;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_group.OoiGroupMapperCreator;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.generation.ObservedReportSetGenerable;
import pss.report.observed.ObservedReport;
import pss.runner.cycle.anonymization.AnonymizationCycleRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import java.util.List;
import java.util.Set;

public class

AnonymizingRunner implements PssRunnable {
    protected final PssType pssType;
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssGroupable pssGrouper;
    protected final UserGenerable userGenerator;
    protected final UserGroupable userGrouper;
    protected final OoiUserGroupMapperFactory ooiUserGroupMapperFactory;
    protected final AnonymizerFactory anonymizerFactory;
    protected final OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory;
    protected final AnonymizerSelectorFactory anonymizerSelectorFactory;
    protected final ObservedReportSetGenerable observedReportGenerator;
    protected final AnonymizableReportGenerable anonymizableReportGenerator;
    protected final AnonymityGenerable anonymityGenerator;
    protected final AnonymizationCycleRunnerFactory anonymizationCycleRunnerFactory;


    public AnonymizingRunner(PssType pssType, PssVariableGenerable pssVariableGenerator, PssGroupable pssGrouper, UserGenerable userGenerator, UserGroupable userGrouper, OoiUserGroupMapperFactory ooiUserGroupMapperFactory, AnonymizerFactory anonymizerFactory, OoiGroupMapperCreatorFactory ooiGroupMapperCreatorFactory, AnonymizerSelectorFactory anonymizerSelectorFactory, ObservedReportSetGenerable observedReportGenerator, AnonymizableReportGenerable anonymizableReportGenerator, AnonymityGenerable anonymityGenerator, AnonymizationCycleRunnerFactory anonymizationCycleRunnerFactory) {
        this.pssType = pssType;
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssGrouper = pssGrouper;
        this.userGenerator = userGenerator;
        this.userGrouper = userGrouper;
        this.ooiUserGroupMapperFactory = ooiUserGroupMapperFactory;
        this.anonymizerFactory = anonymizerFactory;
        this.ooiGroupMapperCreatorFactory = ooiGroupMapperCreatorFactory;
        this.anonymizerSelectorFactory = anonymizerSelectorFactory;
        this.observedReportGenerator = observedReportGenerator;
        this.anonymizableReportGenerator = anonymizableReportGenerator;
        this.anonymityGenerator = anonymityGenerator;
        this.anonymizationCycleRunnerFactory = anonymizationCycleRunnerFactory;
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
        AnonymizationCycleRunner anonymizationCycleRunner = anonymizationCycleRunnerFactory.generateAnonymizationCycleRunner(pssType, ooiUserGroupMapper, anonymizerSelector, userMapper, anonymityGenerator, anonymizableReportGenerator);
        anonymizationCycleRunner.runCycle(observedReports);
    }
}
