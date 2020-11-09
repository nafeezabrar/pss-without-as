package pss.decodability.calculation.aps;

import pss.data.decodability.DecodabilityType;

import java.util.Map;

public class IntermediatePointsDecodabilityCalculators {
    protected final Map<DecodabilityType, EndPointDecodabilityCalculable> decodabilityCalculators;

    public IntermediatePointsDecodabilityCalculators(Map<DecodabilityType, EndPointDecodabilityCalculable> decodabilityCalculators) {
        this.decodabilityCalculators = decodabilityCalculators;
    }
}
