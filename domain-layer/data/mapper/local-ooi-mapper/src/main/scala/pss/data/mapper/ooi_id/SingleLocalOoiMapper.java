package pss.data.mapper.ooi_id;

import pss.data.ooi.Ooi;

import java.util.Map;

public class SingleLocalOoiMapper extends LocalOoiMapper {
    protected final Map<Ooi, Integer> ooiToIdMap;
    protected final Map<Integer, Ooi> idToOoiMap;

    public SingleLocalOoiMapper(Map<Ooi, Integer> ooiToIdMap, Map<Integer, Ooi> idToOoiMap) {
        this.ooiToIdMap = ooiToIdMap;
        this.idToOoiMap = idToOoiMap;
    }

    int getOoiId(Ooi ooi) {
        return ooiToIdMap.get(ooi);
    }

    Ooi getOoi(int ooiId) {
        return idToOoiMap.get(ooiId);
    }

    public Map<Ooi, Integer> getOoiToIdMap() {
        return ooiToIdMap;
    }

    public Map<Integer, Ooi> getIdToOoiMap() {
        return idToOoiMap;
    }
}
