package pss.result.presentable.printing;

import pss.exception.PssException;
import pss.writing.textfile.TextFileTargetUserLocationDecodabilityWriter;

import java.io.IOException;
import java.net.URISyntaxException;

import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class TextFileTargetUserLocationDecodabilityPrinter implements TargetUserLocationDecodabilityPrintable {
    protected final String fileName;
    protected final AppendMode appendMode;

    public TextFileTargetUserLocationDecodabilityPrinter(String fileName, AppendMode appendMode) {
        this.fileName = fileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printTargetUserLocationDecodability(double locationDecodability) throws PssException, IOException, URISyntaxException {
        TextFileTargetUserLocationDecodabilityWriter.writeTargetUserLocationDecodability(fileName, locationDecodability, appendMode);
    }
}
