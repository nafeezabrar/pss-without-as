package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.writing.textfile.TextFileMeanValueOfUnequalSizeWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileApsApsDecodabilityResultPrinter implements ApsDecodabilityResultPrintable {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileApsApsDecodabilityResultPrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printDecodability(List<Double> decodabilities) throws PssException, IOException, URISyntaxException {
        TextFileMeanValueOfUnequalSizeWriter meanValueOfUnequalSizeWriter = new TextFileMeanValueOfUnequalSizeWriter(fileName);
        meanValueOfUnequalSizeWriter.writeMeanValues(decodabilities);
    }
}
