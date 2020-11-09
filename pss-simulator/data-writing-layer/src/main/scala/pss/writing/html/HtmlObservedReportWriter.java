package pss.writing.html;

import pss.conversion.data_to_result.tabular.ObservedReportToTabularResultConverter;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class HtmlObservedReportWriter {
    public static void writeObservedReportInHtml(String fileName, PssType pssType, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        ObservedReportToTabularResultConverter resultConverter = new ObservedReportToTabularResultConverter(pssType);
        TabularResult tabularResult = resultConverter.generateObservedReportResult(observedReports, ooiUserGroupMapper);
        HtmlTabularResultWriter htmlTabularResultWriter = new HtmlTabularResultWriter(fileWriter);
        htmlTabularResultWriter.writeResult(tabularResult);
    }
}
