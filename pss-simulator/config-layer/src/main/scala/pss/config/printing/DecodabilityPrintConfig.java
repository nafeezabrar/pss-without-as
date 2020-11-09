package pss.config.printing;

public abstract class DecodabilityPrintConfig {
    protected final PrintingSource printingSource;

    public DecodabilityPrintConfig(PrintingSource printingSource) {
        this.printingSource = printingSource;
    }

    public PrintingSource getPrintingSource() {
        return printingSource;
    }

    public enum PrintingSource {
        INHERITED,
        SEPARATE
    }
}
