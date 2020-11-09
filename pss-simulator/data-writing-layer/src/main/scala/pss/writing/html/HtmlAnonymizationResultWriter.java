package pss.writing.html;

import pss.conversion.data_to_result.tabular.AnonymizationResultToTabularResultConverter;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlAnonymizationResultWriter {
    public static void writeAnonymizationResultInHtml(String fileName, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<AnonymizationResult> anonymizationResults, List<ObservedReport> observedReports) throws IOException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        AnonymizationResultToTabularResultConverter resultConverter = new AnonymizationResultToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateAnonymizationResult(pssType, ooiUserGroupMapper, anonymizationResults, observedReports);
        HtmlTabularResultWriter writer = new HtmlTabularResultWriter(fileWriter);
        writer.writeResult(tabularResult);
    }
}
