package pss.writing.result.text;

import pss.result.presentable.text.TextResult;
import pss.writing.result.ResultWriter;

import java.io.Writer;

public abstract class TextResultWriter extends ResultWriter<TextResult> {

    public TextResultWriter(Writer writer) {
        super(writer);
    }
}
