package pss.config.task;

import pss.config.RandomSource;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.data.dimension.Dimension;

import java.util.Map;

public class PssVariableGenerationConfig {
    protected final Map<Dimension, OoiGenerationMethod> pssVariableGenerationConfigMap;
    protected final OoiValueGenerationMethod ooiValueGenerationMethod;
    protected final RandomSource randomSource;
    protected final SaveConfig saveConfig;
    protected final PrintConfig printConfig;

    public PssVariableGenerationConfig(Map<Dimension, OoiGenerationMethod> pssVariableGenerationConfigMap, OoiValueGenerationMethod ooiValueGenerationMethod, RandomSource randomSource, SaveConfig saveConfig, PrintConfig printConfig) {
        this.pssVariableGenerationConfigMap = pssVariableGenerationConfigMap;
        this.ooiValueGenerationMethod = ooiValueGenerationMethod;
        this.randomSource = randomSource;
        this.saveConfig = saveConfig;
        this.printConfig = printConfig;
    }

    public RandomSource getRandomSource() {
        return randomSource;
    }

    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public PrintConfig getPrintConfig() {
        return printConfig;
    }

    public Map<Dimension, OoiGenerationMethod> getPssVariableGenerationConfigMap() {
        return pssVariableGenerationConfigMap;
    }

    public OoiValueGenerationMethod getOoiValueGenerationMethod() {
        return ooiValueGenerationMethod;
    }

    public enum OoiGenerationMethod {
        FROM_FILE,
        RANDOM,
        ALPHABET_SMALL_LETTER,
        ALPHABET_CAPITAL_LETTER,
        LOCATION_MIN_AREA_POINTS
    }

    public enum OoiValueGenerationMethod {
        SEQUENTIAL
    }
}
