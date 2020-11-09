package pss.decodability;

import pss.data.ooi.combination.OoiCombination;
import pss.data.valuemap.Value;

import java.util.Map;

public class FullDecodabilityResult extends DecodabilityResult {
    protected final double fullDecodability;
    protected final Map<OoiCombination, Value> decodedValueMap;

    public FullDecodabilityResult(double fullDecodability, Map<OoiCombination, Value> decodedValueMap) {
        this.fullDecodability = fullDecodability;
        this.decodedValueMap = decodedValueMap;
    }

    @Override
    public String toString() {
        return fullDecodability + " ";
    }
}
