package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.full_cycle.FullCycleResult;
import pss.writing.html.HtmlSimplestApsAdversaryResultWriter;

import java.io.IOException;
import java.util.List;

public class HtmlSimplestApsAdversaryResultPrinter implements AdversaryResultPrintable<AdversaryResultWithSingleOcTable> {
    protected final String fileName;

    public HtmlSimplestApsAdversaryResultPrinter(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public void printAdversaryResult(AdversaryResultWithSingleOcTable adversaryResult, List<FullCycleResult> fullCycleResults, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<ObservedReport> observedReports, String resultDirectory) throws IOException, PssException, ClassNotFoundException {
        HtmlSimplestApsAdversaryResultWriter.writeAdversaryResultInHtml(fileName, pssType, ooiUserGroupMapper, fullCycleResults, observedReports, adversaryResult, resultDirectory);
    }
}
