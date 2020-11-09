package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.writing.textfile.TextFileValueWriter;

import java.io.IOException;
import java.net.URISyntaxException;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileValuePrinter<T> implements ValuePrintable<T> {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileValuePrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printValue(T value) throws PssException, IOException, URISyntaxException {
        TextFileValueWriter resultWriter = new TextFileValueWriter();
        resultWriter.writeValueInTextFile(fileName, value, appendMode);
    }
}
