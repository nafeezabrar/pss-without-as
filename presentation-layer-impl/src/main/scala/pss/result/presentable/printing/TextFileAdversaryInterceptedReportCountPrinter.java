package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.writing.textfile.TextFileAdversaryInterceptedReportCountWriter;

import java.io.IOException;
import java.net.URISyntaxException;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileAdversaryInterceptedReportCountPrinter implements AdversaryInterceptedReportCountPrintable {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileAdversaryInterceptedReportCountPrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printReportCounts(int totalReportCount) throws PssException, IOException, URISyntaxException {
        TextFileAdversaryInterceptedReportCountWriter.writeReportCountInTextFile(fileName, totalReportCount, appendMode);
    }
}
