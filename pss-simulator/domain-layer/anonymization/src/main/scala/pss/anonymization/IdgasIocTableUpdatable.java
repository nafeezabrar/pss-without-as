package pss.anonymization;

import pss.data.anonymity.Anonymity;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;

public interface IdgasIocTableUpdatable<TAnonymizedOois extends AnonymizedLocalOoiSet, TLocalOoiCombination extends LocalOoiCombination,
        TAnonymity extends Anonymity> {
    TAnonymizedOois getAnonymizedOois(TLocalOoiCombination ooiCombination, TAnonymity anonymity);

    void update(TLocalOoiCombination ooiCombination, TAnonymizedOois anonymizedOois);
}
