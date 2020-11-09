package pss.config.printing;

public class SeperateDecodabilityPrintConfig extends DecodabilityPrintConfig {
    protected final ShouldPrintConfig printConfig;

    public SeperateDecodabilityPrintConfig(PrintingSource printingSource, ShouldPrintConfig printConfig) {
        super(printingSource);
        this.printConfig = printConfig;
    }

    public ShouldPrintConfig getPrintConfig() {
        return printConfig;
    }
}
