package pss.local.ooi.anonymized;

import java.util.Set;

public class SingleAnonymizedLocalOoiSet extends AnonymizedLocalOoiSet {
    protected final Set<Integer> anonymizedIdSet;

    public SingleAnonymizedLocalOoiSet(Set<Integer> anonymizedIdSet) {
        this.anonymizedIdSet = anonymizedIdSet;
    }

    public Set<Integer> getAnonymizedIdSet() {
        return anonymizedIdSet;
    }

    public int size() {
        return anonymizedIdSet.size();
    }

}
