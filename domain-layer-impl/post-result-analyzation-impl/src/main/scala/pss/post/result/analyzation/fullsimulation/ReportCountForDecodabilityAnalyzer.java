package pss.post.result.analyzation.fullsimulation;

import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.ValuePrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public class ReportCountForDecodabilityAnalyzer implements FullSimulationPostResultAnalyzer {
    protected final FullSimulationPostResultAnalyzer analyzer;
    protected final ValuePrintable valuePrinter;

    public ReportCountForDecodabilityAnalyzer(FullSimulationPostResultAnalyzer analyzer, ValuePrintable valuePrinter) {
        this.analyzer = analyzer;
        this.valuePrinter = valuePrinter;
    }


    @Override
    public void analyzeResult(PssVariables pssVariables, Set<PssGroup> pssGroups, Set<User> users, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults) throws PssException, IOException, URISyntaxException {
        analyzer.analyzeResult(pssVariables, pssGroups, users, observedReports, ooiUserGroupMapper, fullCycleResults);
        int totalValues = pssVariables.getValueMap().getValues().size();
        int reportCountForDecoding = 0;
        double decodability = 0.0;
        int index = 0;
        while (decodability < 1.0 && index < fullCycleResults.size()) {
            FullCycleResult fullCycleResult = fullCycleResults.get(index++);
            int totalDecoded = fullCycleResult.getTotalDecoded();
            if (totalDecoded == totalValues)
                decodability = 1.0;
            reportCountForDecoding++;
        }
        valuePrinter.printValue(reportCountForDecoding);
    }
}
