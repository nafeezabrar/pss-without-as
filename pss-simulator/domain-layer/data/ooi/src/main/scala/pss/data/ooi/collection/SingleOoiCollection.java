package pss.data.ooi.collection;

import pss.data.ooi.Ooi;

import java.util.Set;

public class SingleOoiCollection extends OoiCollection {
    protected final Set<Ooi> ooiSet;

    public SingleOoiCollection(Set<Ooi> ooiSet) {
        this.ooiSet = ooiSet;
    }

    public Set<Ooi> getOoiSet() {
        return ooiSet;
    }

    @Override
    public boolean containsOoi(Ooi ooi) {
        return ooiSet.contains(ooi);
    }
}
