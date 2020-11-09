package pss.data.ooi.collection;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;

import java.util.Map;
import java.util.Set;

public class MultiOoiCollection extends OoiCollection {
    protected final Map<Dimension, Set<Ooi>> ooiSetMap;

    public MultiOoiCollection(Map<Dimension, Set<Ooi>> ooiSetMap) {
        this.ooiSetMap = ooiSetMap;
    }

    public Map<Dimension, Set<Ooi>> getOoiSetMap() {
        return ooiSetMap;
    }

    @Override
    public boolean containsOoi(Ooi ooi) {
        for (Set<Ooi> ooiSet : ooiSetMap.values()) {
            if (ooiSet.contains(ooi))
                return true;
        }
        return false;
    }
}
