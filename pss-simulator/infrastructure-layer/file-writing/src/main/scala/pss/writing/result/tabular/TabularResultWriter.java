package pss.writing.result.tabular;

import pss.result.presentable.tabular.TabularResult;
import pss.writing.result.ResultWriter;

import java.io.Writer;

public abstract class TabularResultWriter extends ResultWriter<TabularResult> {

    public TabularResultWriter(Writer writer) {
        super(writer);
    }
}
