package pss.post.result.analyzation.fullsimulation;

import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.ReportCountAnalyzer;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.CountByUserPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObservedReportedValueCountByUserAnalyzer implements FullSimulationPostResultAnalyzer {
    protected final FullSimulationPostResultAnalyzer analyzer;
    protected final CountByUserPrintable reportCountPrinter;

    public ObservedReportedValueCountByUserAnalyzer(FullSimulationPostResultAnalyzer analyzer, CountByUserPrintable reportCountPrinter) {
        this.analyzer = analyzer;
        this.reportCountPrinter = reportCountPrinter;
    }


    @Override
    public void analyzeResult(PssVariables pssVariables, Set<PssGroup> pssGroups, Set<User> users, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults) throws PssException, IOException, URISyntaxException {
        analyzer.analyzeResult(pssVariables, pssGroups, users, observedReports, ooiUserGroupMapper, fullCycleResults);
        Map<User, Integer> reportCountMap = ReportCountAnalyzer.generateObservedReportValueCountMap(observedReports, ooiUserGroupMapper);
        reportCountPrinter.printReportCount(reportCountMap);
    }
}
