package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.writing.textfile.TextFileAdversaryOwnReportCountWriter;

import java.io.IOException;
import java.net.URISyntaxException;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileAdversaryOwnReportCountPrinter implements AdversaryOwnReportCountPrintable {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileAdversaryOwnReportCountPrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printReportCounts(int reportCount) throws PssException, IOException, URISyntaxException {
        TextFileAdversaryOwnReportCountWriter.writeReportCountInTextFile(fileName, reportCount, appendMode);
    }
}
