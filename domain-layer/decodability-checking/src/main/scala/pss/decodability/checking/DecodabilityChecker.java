package pss.decodability.checking;


import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.valuemap.Value;

import java.util.Map;

public interface DecodabilityChecker<TLocalOoiCombination extends LocalOoiCombination> {
    boolean isDecoded(Value value);

    TLocalOoiCombination getDecodedLocalOoiCombination(Value value);

    Map<TLocalOoiCombination, Value> getDecodedValueMap();
}
