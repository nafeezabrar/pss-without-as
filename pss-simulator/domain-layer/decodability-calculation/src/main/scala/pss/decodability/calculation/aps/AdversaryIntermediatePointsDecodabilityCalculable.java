package pss.decodability.calculation.aps;

import pss.data.decodability.DecodabilityType;
import pss.decodability.DecodabilityResult;
import pss.exception.PssException;
import pss.report.finalreport.FinalReport;
import pss.result.adversary.AdversaryResult;

import java.io.IOException;

public interface AdversaryIntermediatePointsDecodabilityCalculable<TDecodabilityResult extends DecodabilityResult, TAdversaryResult extends AdversaryResult> {
    TDecodabilityResult calculateDecodability(TAdversaryResult adversaryResult, FinalReport finalReport) throws PssException, IOException, ClassNotFoundException;

    DecodabilityType getDecodabilityType();
}
