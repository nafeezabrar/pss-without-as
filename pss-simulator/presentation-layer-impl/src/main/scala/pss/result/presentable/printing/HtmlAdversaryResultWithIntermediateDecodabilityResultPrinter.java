package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.full_cycle.FullCycleResult;
import pss.writing.html.HtmlSimpleAdversaryResultWithIntermediateDecodabilityWriter;

import java.io.IOException;
import java.util.List;

public class HtmlAdversaryResultWithIntermediateDecodabilityResultPrinter implements AdversaryResultWithIntermediateDecodabilityResultPrintable {
    protected final String fileName;

    public HtmlAdversaryResultWithIntermediateDecodabilityResultPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printAdversaryResultWithIntermediateDecodability(List<FullCycleResult> fullCycleResults, PssType pssType, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryResultWithSingleOcTable, IntermediatePointDecodabilityResults decodabilityResultMaps, String resultDirectory) throws IOException, PssException, ClassNotFoundException {
        HtmlSimpleAdversaryResultWithIntermediateDecodabilityWriter.writeAdversaryResultInHtml(fileName, pssType, ooiUserGroupMapper, fullCycleResults, observedReports, adversaryResultWithSingleOcTable, decodabilityResultMaps, resultDirectory);
    }
}
