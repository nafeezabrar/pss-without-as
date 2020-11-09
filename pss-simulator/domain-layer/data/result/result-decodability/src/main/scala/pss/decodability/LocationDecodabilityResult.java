package pss.decodability;

import pss.data.ooi.LocationOoi;
import pss.data.valuemap.Value;

import java.util.Map;

public class LocationDecodabilityResult extends DecodabilityResult {
    protected final double decodability;
    protected final Map<Value, LocationOoi> decodedValueMap;
    protected final TargetUserLocationDecodabilityResult targetUserLocationDecodabilityResult;

    public LocationDecodabilityResult(double decodability, Map<Value, LocationOoi> decodedValueMap, TargetUserLocationDecodabilityResult targetUserLocationDecodabilityResult) {
        this.decodability = decodability;
        this.decodedValueMap = decodedValueMap;
        this.targetUserLocationDecodabilityResult = targetUserLocationDecodabilityResult;
    }

    @Override
    public String toString() {
        String decodabilityResult = decodability + " ";
        return decodabilityResult;
    }

    public TargetUserLocationDecodabilityResult getTargetUserLocationDecodabilityResult() {
        return targetUserLocationDecodabilityResult;
    }

    public double getDecodability() {
        return decodability;
    }

    public Map<Value, LocationOoi> getDecodedValueMap() {
        return decodedValueMap;
    }
}
