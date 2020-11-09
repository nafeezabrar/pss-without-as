package pss.decodability.calculation.aps;

import pss.data.decodability.DecodabilityType;
import pss.decodability.DecodabilityResult;
import pss.exception.PssException;

public interface EndPointDecodabilityCalculable<TDecodabilityResult extends DecodabilityResult> {
    TDecodabilityResult calculateDecodability() throws PssException;

    DecodabilityType getDecodabilityType();
}
