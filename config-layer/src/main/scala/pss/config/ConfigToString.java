package pss.config;

import pss.data.decodability.DecodabilityCalculationPeriod;
import pss.exception.PssException;

import static pss.config.ConfigKeyString.DecodabilityCalculation.calculationEndPoint;
import static pss.config.ConfigKeyString.DecodabilityCalculation.calculationIntermediatePoints;

public class ConfigToString {
    public static String getDecodabilityCalculationPeriodString(DecodabilityCalculationPeriod calculationPeriod) throws PssException {
        switch (calculationPeriod) {

            case INTERMEDIATE_POINTS_CALCULATION:
                return calculationIntermediatePoints;
            case END_POINT_CALCULATION:
                return calculationEndPoint;
        }
        throw new PssException(String.format("decodability string not found for decodability calculation period %s", calculationPeriod.toString()));
    }
}
