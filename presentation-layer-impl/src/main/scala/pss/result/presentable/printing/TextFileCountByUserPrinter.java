package pss.result.presentable.printing;

import pss.data.user.User;
import pss.exception.PssException;
import pss.writing.textfile.TextFileReportCountUserMapWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileCountByUserPrinter implements CountByUserPrintable {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileCountByUserPrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printReportCount(Map<User, Integer> reportCountMap) throws PssException, IOException, URISyntaxException {
        TextFileReportCountUserMapWriter.writeReportCountInTextFile(fileName, reportCountMap, appendMode);
    }
}
