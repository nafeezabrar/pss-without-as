package pss.writing.html;

import pss.conversion.data_to_result.tabular.OoiGroupMapperToTabularResultConverter;
import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.library.FileUtility;
import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.tabular.HtmlTabularResultWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlOoiGroupMapperWriter {
    public static void writeOoiGroupMapperInHtml(String fileName, OoiGroupMapper ooiGroupMapper, PssType pssType, PssVariables pssVariables) throws IOException, URISyntaxException, PssException {
        FileWriter fileWriter = FileUtility.createFileWriteSafely(fileName);
        OoiGroupMapperToTabularResultConverter resultConverter = new OoiGroupMapperToTabularResultConverter();
        TabularResult tabularResult = resultConverter.generateOoiGroupMapperResult(pssType, pssVariables, ooiGroupMapper);
        HtmlTabularResultWriter htmlTabularResultWriter = new HtmlTabularResultWriter(fileWriter);
        htmlTabularResultWriter.writeResult(tabularResult);
    }
}
