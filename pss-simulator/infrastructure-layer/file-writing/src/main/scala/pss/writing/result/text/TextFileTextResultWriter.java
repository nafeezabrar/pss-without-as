package pss.writing.result.text;

import pss.result.presentable.text.TextResult;

import java.io.IOException;
import java.io.Writer;

public class TextFileTextResultWriter extends TextResultWriter {
    public TextFileTextResultWriter(Writer writer) {
        super(writer);
    }

    @Override
    public void writeResult(TextResult result) throws IOException {
        for (String line : result.getResultText()) {
            writer.append(line);
            writer.append("\n");
        }
        writer.close();
    }
}
