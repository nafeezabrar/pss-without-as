package pss.decodability.calculation.aps;

import pss.data.decodability.DecodabilityType;

import java.util.Map;

public class EndPointDecodabilityCalculators {
    protected final Map<DecodabilityType, EndPointDecodabilityCalculable> decodabilityCalculators;

    public EndPointDecodabilityCalculators(Map<DecodabilityType, EndPointDecodabilityCalculable> decodabilityCalculators) {
        this.decodabilityCalculators = decodabilityCalculators;
    }
}
