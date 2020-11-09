package pss.runner;

import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.factory.report.generation.cycle_runner.anonymization.DeanonymizationCycleRunnerFactory;
import pss.factory.report.generation.deanonymizer.DeanonymizerSelectorFactory;
import pss.factory.report.generation.deanonymizer.DeanonymizerSetFactory;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;
import pss.mapper.user.UserMapper;
import pss.report.finalreport.FinalReport;
import pss.report.generation.FinalReportDirectGenerable;
import pss.runner.cycle.deanonymization.DeanonymizationCycleRunner;
import pss.user.generation.UserGenerable;
import pss.user.grouping.UserGroupable;
import pss.variable.generation.PssVariableGenerable;
import pss.variable.grouping.PssGroupable;

import java.util.List;
import java.util.Set;

public class DeanonymizingRunner implements PssRunnable {
    protected final PssType pssType;
    protected final PssVariableGenerable pssVariableGenerator;
    protected final PssGroupable pssGrouper;
    protected final UserGenerable userGenerator;
    protected final UserGroupable userGrouper;
    protected final FinalReportDirectGenerable finalReportGenerator;
    protected final DeanonymizerSetFactory deanonymizerSetFactory;
    protected final DeanonymizerSelectorFactory deanonymizerChooserFactory;
    protected final DeanonymizationCycleRunnerFactory deanonymizationCycleRunnerFactory;

    public DeanonymizingRunner(PssType pssType, PssVariableGenerable pssVariableGenerator, PssGroupable pssGrouper, UserGenerable userGenerator, UserGroupable userGrouper, FinalReportDirectGenerable finalReportGenerator, DeanonymizerSetFactory deanonymizerSetFactory, DeanonymizerSelectorFactory deanonymizerChooserFactory, DeanonymizationCycleRunnerFactory deanonymizationCycleRunnerFactory) {
        this.pssType = pssType;
        this.pssVariableGenerator = pssVariableGenerator;
        this.pssGrouper = pssGrouper;
        this.userGenerator = userGenerator;
        this.userGrouper = userGrouper;
        this.finalReportGenerator = finalReportGenerator;
        this.deanonymizerSetFactory = deanonymizerSetFactory;
        this.deanonymizerChooserFactory = deanonymizerChooserFactory;
        this.deanonymizationCycleRunnerFactory = deanonymizationCycleRunnerFactory;
    }


    @Override
    public void run() throws Exception {
        PssVariables pssVariables = pssVariableGenerator.generatePssVariables(pssType);
        Set<PssGroup> pssGroups = pssGrouper.generateGroups(pssType, pssVariables);
        UserMapper userMapper = userGenerator.generateUsers();
        UserGroupMapper userGroupMapper = userGrouper.assignGroup(userMapper.getUsers(), pssGroups);
        DeanonymizerGroupMapper deanonymizerGroupMapper = deanonymizerSetFactory.createDeanonymizer(pssGroups);
        DeanonymizerSelector deanonymizerSelector = deanonymizerChooserFactory.generateDeanonymizerChooser(userGroupMapper, deanonymizerGroupMapper);
        DeanonymizationCycleRunner deanonymizationCycleRunner = deanonymizationCycleRunnerFactory.generateCycleRunner(deanonymizerSelector);
        List<FinalReport> finalReports = finalReportGenerator.generateDirectFinalReports();
        deanonymizationCycleRunner.runCycle(finalReports);
    }
}
