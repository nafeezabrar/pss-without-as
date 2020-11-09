package pss.result.presentable.printing;

import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.decodability.EndPointDecodabilityResults;
import pss.exception.PssException;
import pss.writing.textfile.TextFileDecodabilityWriter;

import java.io.IOException;
import java.net.URISyntaxException;

public class TextFileSeparateEndPointDecodabilityPrinter implements EndPointDecodabilityPrintable {

    protected final String decodabilityResultFileName;
    protected final String decodedValueFileName;
    protected final AppendMode appendMode;

    public TextFileSeparateEndPointDecodabilityPrinter(String decodabilityResultFileName, String decodedValueFileName, AppendMode appendMode) {
        this.decodabilityResultFileName = decodabilityResultFileName;
        this.decodedValueFileName = decodedValueFileName;
        this.appendMode = appendMode;
    }

    @Override
    public void printDecodability(EndPointDecodabilityResults decodabilityResults) throws PssException, IOException, URISyntaxException {
        TextFileDecodabilityWriter.writeDecodabilityInTextFile(decodabilityResultFileName, decodabilityResults, appendMode);
        TextFileDecodabilityWriter.writeDecodedValueCountInTextFile(decodedValueFileName, decodabilityResults, appendMode);
    }
}
