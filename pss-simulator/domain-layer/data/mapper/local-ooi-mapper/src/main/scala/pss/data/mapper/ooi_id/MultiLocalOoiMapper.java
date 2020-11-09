package pss.data.mapper.ooi_id;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;

import java.util.Map;

public class MultiLocalOoiMapper extends LocalOoiMapper {
    protected final Map<Dimension, Map<Ooi, Integer>> ooiToIdMaps;
    protected final Map<Dimension, Map<Integer, Ooi>> idToOoiMaps;

    public MultiLocalOoiMapper(Map<Dimension, Map<Ooi, Integer>> ooiToIdMaps, Map<Dimension, Map<Integer, Ooi>> idToOoiMaps) {
        this.ooiToIdMaps = ooiToIdMaps;
        this.idToOoiMaps = idToOoiMaps;
    }

    int getOoiId(Ooi ooi, Dimension dimension) {
        Map<Ooi, Integer> ooiToIdMap = ooiToIdMaps.get(dimension);
        return ooiToIdMap.get(ooi);
    }

    Ooi getOoi(int ooiId, Dimension dimension) {
        Map<Integer, Ooi> idToOoiMap = idToOoiMaps.get(dimension);
        return idToOoiMap.get(ooiId);
    }

    public Map<Dimension, Map<Ooi, Integer>> getOoiToIdMaps() {
        return ooiToIdMaps;
    }

    public Map<Dimension, Map<Integer, Ooi>> getIdToOoiMaps() {
        return idToOoiMaps;
    }
}
