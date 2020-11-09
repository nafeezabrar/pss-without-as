package pss.result.presentable.printing;

import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.writing.textfile.TextFileMeanValueOfEqualSizeWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.Map;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileReportCountByGroupPrinter implements ObservedReportByGroupPrintable {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileReportCountByGroupPrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printObservedReportByGroup(Map<PssGroup, Integer> reportCountMap) throws PssException, IOException, URISyntaxException {
        Comparator<PssGroup> keyComparator = (o1, o2) -> Integer.compare(o1.getPssGroupId(), o2.getPssGroupId());
        TextFileMeanValueOfEqualSizeWriter meanValueOfEqualSizeWriter = new TextFileMeanValueOfEqualSizeWriter(fileName, keyComparator);
        meanValueOfEqualSizeWriter.writeReportCountOfEachGroup(reportCountMap);
    }
}
