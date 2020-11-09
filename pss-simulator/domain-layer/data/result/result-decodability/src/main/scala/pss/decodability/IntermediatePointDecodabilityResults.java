package pss.decodability;

import pss.data.decodability.DecodabilityType;
import pss.result.full_cycle.FullCycleResult;

import java.util.Map;
import java.util.Set;

public class IntermediatePointDecodabilityResults {
    protected final Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> decodabilityResultMaps;
    protected final Set<DecodabilityType> decodabilityTypes;

    public IntermediatePointDecodabilityResults(Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> decodabilityResultMaps, Set<DecodabilityType> decodabilityTypes) {
        this.decodabilityResultMaps = decodabilityResultMaps;
        this.decodabilityTypes = decodabilityTypes;
    }

    public Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> getDecodabilityResultMaps() {
        return decodabilityResultMaps;
    }

    public Set<DecodabilityType> getDecodabilityTypes() {
        return decodabilityTypes;
    }
}
