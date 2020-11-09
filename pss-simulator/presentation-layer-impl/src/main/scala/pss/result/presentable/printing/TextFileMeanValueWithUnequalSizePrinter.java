package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.writing.textfile.TextFileMeanValueOfUnequalSizeWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class TextFileMeanValueWithUnequalSizePrinter<T> implements MeanValuePrintable<T> {
    protected final String fileName;

    public TextFileMeanValueWithUnequalSizePrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printValue(List<T> values) throws PssException, IOException, URISyntaxException {
        TextFileMeanValueOfUnequalSizeWriter valueWriter = new TextFileMeanValueOfUnequalSizeWriter(fileName);
        valueWriter.writeMeanValues(values);
    }
}
