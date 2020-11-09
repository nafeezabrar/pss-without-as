package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.writing.textfile.TextFileAdversaryOwnReportedValueCountWriter;

import java.io.IOException;
import java.net.URISyntaxException;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileAdversaryOwnReportedValueCountPrinter implements AdversaryOwnReportedValueCountPrintable {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileAdversaryOwnReportedValueCountPrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printReportedValueCount(int reportedValueCount) throws PssException, IOException, URISyntaxException {
        TextFileAdversaryOwnReportedValueCountWriter.writeReportedValueCountInTextFile(fileName, reportedValueCount, appendMode);
    }
}
