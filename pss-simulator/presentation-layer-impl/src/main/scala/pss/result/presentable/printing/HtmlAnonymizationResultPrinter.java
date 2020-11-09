package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.writing.html.HtmlAnonymizationResultWriter;

import java.io.IOException;
import java.util.List;

public class HtmlAnonymizationResultPrinter implements AnonymizationResultPrintable {
    protected final String fileName;

    public HtmlAnonymizationResultPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printAnonymizationResult(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<AnonymizationResult> anonymizationResults, List<ObservedReport> observedReports) throws IOException, PssException {
        HtmlAnonymizationResultWriter.writeAnonymizationResultInHtml(fileName, pssType, ooiUserGroupMapper, anonymizationResults, observedReports);
    }
}
