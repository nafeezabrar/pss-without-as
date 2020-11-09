package pss.config.runner;

import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public class FullCycleRunnerConfig extends CycleRunnerConfig {
    protected final DecodabilityCalculationConfig decodabilityCalculationConfig;

    public FullCycleRunnerConfig(SaveConfig saveConfig, PrintConfig printConfig, DecodabilityCalculationConfig decodabilityCalculationConfigPastPast) {
        super(saveConfig, printConfig);
        this.decodabilityCalculationConfig = decodabilityCalculationConfigPastPast;
    }

    public DecodabilityCalculationConfig getDecodabilityCalculationConfig() {
        return decodabilityCalculationConfig;
    }
}
