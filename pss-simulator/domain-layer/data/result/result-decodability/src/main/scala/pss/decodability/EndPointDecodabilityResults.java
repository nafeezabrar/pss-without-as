package pss.decodability;

import pss.data.decodability.DecodabilityType;

import java.util.Map;
import java.util.Set;

public class EndPointDecodabilityResults {
    protected final Map<DecodabilityType, DecodabilityResult> decodabilityResultMap;

    public EndPointDecodabilityResults(Map<DecodabilityType, DecodabilityResult> decodabilityResultMap) {
        this.decodabilityResultMap = decodabilityResultMap;
    }

    public Map<DecodabilityType, DecodabilityResult> getDecodabilityResultMap() {
        return decodabilityResultMap;
    }

    public Set<DecodabilityType> getDecodabilityTypes() {
        return decodabilityResultMap.keySet();
    }
}
