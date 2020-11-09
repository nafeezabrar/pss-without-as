package pss.writing.html;

import pss.conversion.data_to_result.tabular.FullCycleResultToTabularResultConverter;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlFullCycleResultWriter {
    public static void writeFullCycleResultInHtml(String fileName, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports) throws IOException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        FullCycleResultToTabularResultConverter resultConverter = new FullCycleResultToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateFullCycleResult(pssType, ooiUserGroupMapper, fullCycleResults, observedReports);
        HtmlTabularResultWriter writer = new HtmlTabularResultWriter(fileWriter);
        writer.writeResult(tabularResult);
    }
}
