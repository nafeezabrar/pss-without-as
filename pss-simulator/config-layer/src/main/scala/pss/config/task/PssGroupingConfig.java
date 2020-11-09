package pss.config.task;

import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.data.dimension.Dimension;

import java.util.Map;

public class PssGroupingConfig {
    protected final PssGroupingMethod pssGroupingMethod;
    protected final Map<Dimension, Integer> ooiInEachGroupMap;

    protected final SaveConfig saveConfig;
    protected final PrintConfig printConfig;
    protected final boolean printOoiMapper;


    public PssGroupingConfig(PssGroupingMethod pssGroupingMethod, Map<Dimension, Integer> ooiInEachGroupMap, SaveConfig saveConfig, PrintConfig printConfig, boolean printOoiMapper) {
        this.pssGroupingMethod = pssGroupingMethod;
        this.ooiInEachGroupMap = ooiInEachGroupMap;
        this.saveConfig = saveConfig;
        this.printConfig = printConfig;
        this.printOoiMapper = printOoiMapper;
    }

    public PssGroupingMethod getPssGroupingMethod() {
        return pssGroupingMethod;
    }

    public Map<Dimension, Integer> getOoiInEachGroupMap() {
        return ooiInEachGroupMap;
    }

    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public PrintConfig getPrintConfig() {
        return printConfig;
    }

    public boolean isPrintOoiMapper() {
        return printOoiMapper;
    }

    public enum PssGroupingMethod {
        ONE_DIMENSION_GROUPING,
        ALL_DIMENSION_GROUPING,
        ALL_DIMENSION_SEQUENTIAL_GROUPING
    }
}
