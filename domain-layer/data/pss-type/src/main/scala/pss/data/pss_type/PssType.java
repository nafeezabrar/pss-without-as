package pss.data.pss_type;

import pss.data.dimension.Dimension;
import pss.exception.PssException;

import java.util.Map;
import java.util.Set;

import static pss.data.pss_type.PssType.PssDimensionType.SINGLE;

public class PssType {
    protected final PssDimensionType pssDimensionType;
    protected final Map<Dimension, Integer> nSet;

    public PssType(PssDimensionType pssDimensionType, Map<Dimension, Integer> nSet) {
        this.pssDimensionType = pssDimensionType;
        this.nSet = nSet;
    }


    public PssDimensionType getPssDimensionType() {
        return pssDimensionType;
    }

    public Set<Dimension> getDimensionSet() {
        return nSet.keySet();
    }

    public Map<Dimension, Integer> getnSet() {
        return nSet;
    }

    public int getN() throws PssException {
        if (pssDimensionType == SINGLE) {
            Map.Entry<Dimension, Integer> nMap = nSet.entrySet().iterator().next();
            return nMap.getValue();
        }
        throw new PssException("getN called for multi pss type!!");
    }

    public Dimension getDimension() throws PssException {
        if (pssDimensionType == SINGLE) {
            Map.Entry<Dimension, Integer> nMap = nSet.entrySet().iterator().next();
            return nMap.getKey();
        }
        throw new PssException("getDimension called for multi pss type!!");
    }

    public enum PssDimensionType {
        SINGLE, MULTI
    }
}
