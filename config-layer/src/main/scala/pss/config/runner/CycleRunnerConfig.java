package pss.config.runner;

import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public class CycleRunnerConfig {
    protected final SaveConfig saveConfig;
    protected final PrintConfig printConfig;

    public CycleRunnerConfig(SaveConfig saveConfig, PrintConfig printConfig) {
        this.saveConfig = saveConfig;
        this.printConfig = printConfig;
    }

    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public PrintConfig getPrintConfig() {
        return printConfig;
    }
}
