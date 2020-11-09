package pss.writing.result;

import pss.result.presentable.PresentableResult;

import java.io.IOException;
import java.io.Writer;

public abstract class ResultWriter<TPresentableResult extends PresentableResult> {
    protected final Writer writer;

    public ResultWriter(Writer writer) {
        this.writer = writer;
    }

    public abstract void writeResult(TPresentableResult result) throws IOException;
}
