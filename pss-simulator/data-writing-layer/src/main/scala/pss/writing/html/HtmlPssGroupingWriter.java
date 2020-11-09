package pss.writing.html;

import pss.conversion.data_to_result.tabular.PssGroupingToTabularResultConverter;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class HtmlPssGroupingWriter {
    public static void writePssGroupingInHtml(String fileName, Set<PssGroup> pssGroups, PssType pssType) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        PssGroupingToTabularResultConverter resultConverter = new PssGroupingToTabularResultConverter(pssGroups, pssType);
        TabularResult tabularResult = resultConverter.generatePssGroupingResult();
        HtmlTabularResultWriter htmlTabularResultWriter = new HtmlTabularResultWriter(fileWriter);
        htmlTabularResultWriter.writeResult(tabularResult);
    }
}
