package pss.post.result.analyzation.fullsimulation;

import com.pss.adversary.Adversary;
import pss.data.pssvariable.PssVariables;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.presentable.printing.MeanValuePrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdversaryOwnReportedDecodabilityAnalyzer implements FullSimulationWithAdversaryPostResultAnalyzer {
    protected final FullSimulationWithAdversaryPostResultAnalyzer analyzer;
    protected final MeanValuePrintable meanValuePrinter;

    public AdversaryOwnReportedDecodabilityAnalyzer(FullSimulationWithAdversaryPostResultAnalyzer analyzer, MeanValuePrintable meanValuePrinter) {
        this.analyzer = analyzer;
        this.meanValuePrinter = meanValuePrinter;
    }


    @Override
    public void analyzeResult(Adversary adversary, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, AdversaryResult adversaryResult, PssVariables pssVariables) throws PssException, IOException, URISyntaxException {
        analyzer.analyzeResult(adversary, observedReports, ooiUserGroupMapper, adversaryResult, pssVariables);

        int totalValues = pssVariables.getValueMap().getValues().size();
        List<Double> decodabilities = new ArrayList<>();
        List<Integer> leakedReportIds = new ArrayList<>();
        List<FinalReport> leakedReports = adversaryResult.getLeakedReports();
        for (FinalReport finalReport : leakedReports) {
            leakedReportIds.add(finalReport.getId());
        }
        Set<Integer> adversaryUserIds = adversary.getAdversaryUserIds();
        Set<Value> decodedValues = new HashSet<>();
        for (ObservedReport observedReport : observedReports) {
            int userId = observedReport.getReportData().getUserId();
            if (adversaryUserIds.contains(userId)) {
                decodedValues.add(observedReport.getValue());
            }
            decodabilities.add((double) decodedValues.size() / totalValues);
        }

        meanValuePrinter.printValue(decodabilities);
    }
}
