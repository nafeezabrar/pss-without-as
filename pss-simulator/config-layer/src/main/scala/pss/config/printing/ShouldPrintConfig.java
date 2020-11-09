package pss.config.printing;

import pss.PrintingTargetType;

public abstract class ShouldPrintConfig extends PrintConfig {
    protected final PrintingTargetType printingTargetType;
    protected final AppendMode appendMode;
    protected final FileNameMode fileNameMode;

    public ShouldPrintConfig(boolean shouldPrint, PrintingTargetType printingTargetType, AppendMode appendMode, FileNameMode fileNameMode) {
        super(shouldPrint);
        this.printingTargetType = printingTargetType;
        this.appendMode = appendMode;
        this.fileNameMode = fileNameMode;
    }


    public PrintingTargetType getPrintingTargetType() {
        return printingTargetType;
    }

    public AppendMode getAppendMode() {
        return appendMode;
    }

    public FileNameMode getFileNameMode() {
        return fileNameMode;
    }
    public enum AppendMode {
        NOT_APPEND,
        SHOULD_APPEND
    }

    public enum FileNameMode {
        GIVEN,
        NOT_GIVEN
    }
}
