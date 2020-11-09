package pss.anonymization;

import pss.data.anonymity.Anonymity;
import pss.data.valuemap.Value;
import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;

public interface AasIocTableUpdatable<TAnonymizedOois extends AnonymizedLocalOoiSet, TAnonymity extends Anonymity> {
    TAnonymizedOois getAnonymizedOois(Value value, TAnonymity anonymity);

    void update(Value value, TAnonymizedOois anonymizedOois);
}
