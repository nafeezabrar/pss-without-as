package pss.post.result.analyzation.fullsimulation;

import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.ApsDecodabilityResultPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ApsDecodabilityAnalyzer implements FullSimulationPostResultAnalyzer {
    protected final FullSimulationPostResultAnalyzer analyzer;
    protected final ApsDecodabilityResultPrintable decodabilityPrinter;

    public ApsDecodabilityAnalyzer(FullSimulationPostResultAnalyzer analyzer, ApsDecodabilityResultPrintable decodabilityPrinter) {
        this.analyzer = analyzer;
        this.decodabilityPrinter = decodabilityPrinter;
    }


    @Override
    public void analyzeResult(PssVariables pssVariables, Set<PssGroup> pssGroups, Set<User> users, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults) throws PssException, IOException, URISyntaxException {
        analyzer.analyzeResult(pssVariables, pssGroups, users, observedReports, ooiUserGroupMapper, fullCycleResults);
        List<Double> decodabilities = new ArrayList<>();
        double totalOoiCombinations = pssVariables.getValueMap().getValues().size();
        for (FullCycleResult fullCycleResult : fullCycleResults) {
            int totalDecoded = fullCycleResult.getTotalDecoded();
            double decodability = totalDecoded / totalOoiCombinations;
            decodabilities.add(decodability);
        }
        decodabilityPrinter.printDecodability(decodabilities);
    }
}
