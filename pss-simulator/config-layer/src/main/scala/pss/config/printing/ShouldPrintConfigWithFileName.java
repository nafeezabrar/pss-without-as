package pss.config.printing;

import pss.PrintingTargetType;

import static pss.config.printing.ShouldPrintConfig.FileNameMode.GIVEN;

public class ShouldPrintConfigWithFileName extends ShouldPrintConfig {

    protected final String fileName;

    public ShouldPrintConfigWithFileName(PrintingTargetType printingTargetType, AppendMode appendMode, String fileName) {
        super(true, printingTargetType, appendMode, GIVEN);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
