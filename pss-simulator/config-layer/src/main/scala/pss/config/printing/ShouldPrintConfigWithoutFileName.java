package pss.config.printing;

import pss.PrintingTargetType;

public class ShouldPrintConfigWithoutFileName extends ShouldPrintConfig {

    public ShouldPrintConfigWithoutFileName(PrintingTargetType printingTargetType, AppendMode appendMode) {
        super(true, printingTargetType, appendMode, FileNameMode.NOT_GIVEN);
    }
}
