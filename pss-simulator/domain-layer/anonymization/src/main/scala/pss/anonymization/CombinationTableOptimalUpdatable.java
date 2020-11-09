package pss.anonymization;

import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.valuemap.Value;

import java.util.Set;

public interface CombinationTableOptimalUpdatable<TLocalOoiCombination extends LocalOoiCombination, TLocalOoiCollection extends LocalOoiCollection> {
    TLocalOoiCollection chooseBestAnonymizedCollectionAndUpdate(TLocalOoiCombination reportedOoiCombination, Value reportedValue, Set<TLocalOoiCollection> possibleAnonymizedCollections);
}
