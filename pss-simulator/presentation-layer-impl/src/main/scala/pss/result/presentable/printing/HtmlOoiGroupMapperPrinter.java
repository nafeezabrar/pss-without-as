package pss.result.presentable.printing;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.writing.html.HtmlOoiGroupMapperWriter;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlOoiGroupMapperPrinter implements OoiGroupMapperPrintable {
    protected final String fileName;

    public HtmlOoiGroupMapperPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printOoiGroupMapper(PssType pssType, PssVariables pssVariables, OoiGroupMapper ooiGroupMapper) throws URISyntaxException, PssException, IOException {
        HtmlOoiGroupMapperWriter.writeOoiGroupMapperInHtml(fileName, ooiGroupMapper, pssType, pssVariables);
    }
}
