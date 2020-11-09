package pss.config.adversary;

import com.pss.adversary.Adversary.AdversaryType;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;

public class AdversaryConfig {
    protected final AdversaryType adversaryType;
    protected final SaveConfig saveConfig;
    protected final PrintConfig printConfig;
    protected final DecodabilityCalculationConfig decodabilityCalculationConfig;

    public AdversaryConfig(AdversaryType adversaryType, SaveConfig saveConfig, PrintConfig printConfig, DecodabilityCalculationConfig decodabilityCalculationConfig) {
        this.adversaryType = adversaryType;
        this.saveConfig = saveConfig;
        this.printConfig = printConfig;
        this.decodabilityCalculationConfig = decodabilityCalculationConfig;
    }

    public AdversaryType getAdversaryType() {
        return adversaryType;
    }

    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public PrintConfig getPrintConfig() {
        return printConfig;
    }

    public DecodabilityCalculationConfig getDecodabilityCalculationConfig() {
        return decodabilityCalculationConfig;
    }
}
