package pss.result.presentable.printing;

import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.writing.html.HtmlPssGroupingWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class HtmlPssGroupPrinter implements PssGroupPrintable {
    protected final String fileName;

    public HtmlPssGroupPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printPssGroups(PssType pssType, Set<PssGroup> pssGroups) throws URISyntaxException, IOException, PssException {
        HtmlPssGroupingWriter.writePssGroupingInHtml(fileName, pssGroups, pssType);
    }
}
