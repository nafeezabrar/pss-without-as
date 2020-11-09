package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.writing.html.HtmlFullCycleResultWriter;

import java.io.IOException;
import java.util.List;

public class HtmlFullCycleResultPrinter implements FullCycleResultPrintable {
    protected final String fileName;

    public HtmlFullCycleResultPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printFullCycleResult(List<FullCycleResult> fullCycleResults, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports) throws IOException, PssException {
        HtmlFullCycleResultWriter.writeFullCycleResultInHtml(fileName, pssType, ooiUserGroupMapper, fullCycleResults, observedReports);
    }
}
