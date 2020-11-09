package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.writing.html.HtmlFullCycleResultWithIntermediateDecodabilityWriter;

import java.io.IOException;
import java.util.List;

public class HtmlFullCycleResultWithIntermediateDecodabilityResultPrinter implements FullCycleResultWithIntermediateDecodabilityResultPrintable {
    protected final String fileName;

    public HtmlFullCycleResultWithIntermediateDecodabilityResultPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printFullCycleResultWithIntermediateDecodability(List<FullCycleResult> fullCycleResults, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, IntermediatePointDecodabilityResults decodabilityResultMaps) throws IOException, PssException {
        HtmlFullCycleResultWithIntermediateDecodabilityWriter.writeFullCycleResultInHtml(fileName, pssType, ooiUserGroupMapper, fullCycleResults, observedReports, decodabilityResultMaps);
    }
}
