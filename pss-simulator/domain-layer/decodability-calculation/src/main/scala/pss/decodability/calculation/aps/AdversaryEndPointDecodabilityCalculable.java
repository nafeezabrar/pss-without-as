package pss.decodability.calculation.aps;

import pss.data.decodability.DecodabilityType;
import pss.decodability.DecodabilityResult;
import pss.exception.PssException;
import pss.result.adversary.AdversaryResult;

public interface AdversaryEndPointDecodabilityCalculable<TDecodabilityResult extends DecodabilityResult, TAdversaryResult extends AdversaryResult> {
    TDecodabilityResult calculateDecodability(TAdversaryResult adversaryResult) throws PssException;

    DecodabilityType getDecodabilityType();
}
