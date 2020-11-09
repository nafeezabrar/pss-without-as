package pss.config.task;

import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.data.dimension.Dimension;

import java.util.Map;

public class MultiPssGroupingConfigOneDimension extends PssGroupingConfig {
    protected final Dimension dividingDimension;

    public MultiPssGroupingConfigOneDimension(PssGroupingMethod pssGroupingMethod, Map<Dimension, Integer> ooiInEachGroupMap, SaveConfig saveConfig, PrintConfig printConfig, boolean printOoiMapper, Dimension dividingDimension) {
        super(pssGroupingMethod, ooiInEachGroupMap, saveConfig, printConfig, printOoiMapper);
        this.dividingDimension = dividingDimension;
    }


    public Dimension getDividingDimension() {
        return dividingDimension;
    }
}
