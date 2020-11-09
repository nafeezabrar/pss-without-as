package pss.decodability.calculation.aps;

import pss.data.decodability.DecodabilityType;
import pss.decodability.DecodabilityResult;
import pss.exception.PssException;
import pss.result.deanonymization.DeanonymizationResult;

public interface IntermediatePointsDecodabilityCalculable<TDecodabilityResult extends DecodabilityResult> {
    TDecodabilityResult calculateDecodability(DeanonymizationResult deanonymizationResult) throws PssException;

    DecodabilityType getDecodabilityType();
}
