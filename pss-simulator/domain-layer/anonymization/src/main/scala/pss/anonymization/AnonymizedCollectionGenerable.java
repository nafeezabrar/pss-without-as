package pss.anonymization;

import pss.data.anonymity.Anonymity;
import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;

import java.util.Set;

public interface AnonymizedCollectionGenerable<TLocalOoiCombination extends LocalOoiCombination, TAnonymity extends Anonymity, TLocalOoiCollection extends LocalOoiCollection> {
    Set<TLocalOoiCollection> generatePossibleAnonymization(TLocalOoiCombination localOoiCombination, TAnonymity anonymity);
}
