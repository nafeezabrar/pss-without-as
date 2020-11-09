package pss.writing.html;

import pss.conversion.data_to_result.tabular.FullCycleResultWithDecodabilityToTabularResultConverter;
import pss.data.pss_type.PssType;
import pss.decodability.IntermediatePointDecodabilityResults;
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

public class HtmlFullCycleResultWithIntermediateDecodabilityWriter {
    public static void writeFullCycleResultInHtml(String fileName, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, IntermediatePointDecodabilityResults decodabilityResultMaps) throws IOException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        FullCycleResultWithDecodabilityToTabularResultConverter resultConverter = new FullCycleResultWithDecodabilityToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateFullCycleWithDecodabilityPresentableResult(pssType, ooiUserGroupMapper, fullCycleResults, observedReports, decodabilityResultMaps);

        HtmlTabularResultWriter writer = new HtmlTabularResultWriter(fileWriter);
        writer.writeResult(tabularResult);
    }
}
