package pss.config.decodability.result;

import pss.config.printing.DecodabilityPrintConfig;
import pss.data.decodability.DecodabilityCalculationPeriod;
import pss.data.decodability.DecodabilityType;

import java.util.Map;
import java.util.Set;

public class DecodabilityCalculationConfig {
    protected final Map<DecodabilityCalculationPeriod, Set<DecodabilityType>> decodabilityMap;
    protected final Map<DecodabilityCalculationPeriod, DecodabilityPrintConfig> decodabilityPrintConfigMap;

    public DecodabilityCalculationConfig(Map<DecodabilityCalculationPeriod, Set<DecodabilityType>> decodabilityMap, Map<DecodabilityCalculationPeriod, DecodabilityPrintConfig> decodabilityPrintConfigMap) {
        this.decodabilityMap = decodabilityMap;
        this.decodabilityPrintConfigMap = decodabilityPrintConfigMap;
    }

    public Map<DecodabilityCalculationPeriod, Set<DecodabilityType>> getDecodabilityMap() {
        return decodabilityMap;
    }

    public Map<DecodabilityCalculationPeriod, DecodabilityPrintConfig> getDecodabilityPrintConfigMap() {
        return decodabilityPrintConfigMap;
    }
}
