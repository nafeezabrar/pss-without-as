package pss.post.result.analyzation.fullsimulation;

import com.pss.adversary.Adversary;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.presentable.printing.ValuePrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class AdversaryOwnReportedValueAnalyzer implements FullSimulationWithAdversaryPostResultAnalyzer {
    protected final FullSimulationWithAdversaryPostResultAnalyzer analyzer;
    protected final ValuePrintable valuePrinter;

    public AdversaryOwnReportedValueAnalyzer(FullSimulationWithAdversaryPostResultAnalyzer analyzer, ValuePrintable valuePrinter) {
        this.analyzer = analyzer;
        this.valuePrinter = valuePrinter;
    }

    @Override
    public void analyzeResult(Adversary adversary, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, AdversaryResult adversaryResult, PssVariables pssVariables) throws PssException, IOException, URISyntaxException {
        analyzer.analyzeResult(adversary, observedReports, ooiUserGroupMapper, adversaryResult, pssVariables);
        int reportedByAdversaryCount = adversaryResult.getReportedValueCountByAdversary();
        valuePrinter.printValue(reportedByAdversaryCount);
    }
}
