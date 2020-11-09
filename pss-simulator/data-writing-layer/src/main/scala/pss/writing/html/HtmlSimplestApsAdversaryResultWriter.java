package pss.writing.html;

import pss.conversion.data_to_result.tabular.SimplestAdversaryResultToTabularResultConverter;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlSimplestApsAdversaryResultWriter {
    public static void writeAdversaryResultInHtml(String fileName, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, AdversaryResultWithSingleOcTable adversaryResult, String resultDirectory) throws IOException, PssException, ClassNotFoundException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        SimplestAdversaryResultToTabularResultConverter resultConverter = new SimplestAdversaryResultToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateAdversaryResult(pssType, ooiUserGroupMapper, fullCycleResults, observedReports, adversaryResult, resultDirectory);
        HtmlTabularResultWriter writer = new HtmlTabularResultWriter(fileWriter);
        writer.writeResult(tabularResult);
    }
}
