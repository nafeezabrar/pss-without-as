package pss.data.mapper.ooi_group;

import pss.data.ooi.combination.OoiCombination;
import pss.data.pssvariable.group.PssGroup;

import java.util.Map;
import java.util.Set;

public class OoiGroupMapper {
    protected final Map<OoiCombination, PssGroup> ooiCombinationToPssGroupMap;
    protected final Map<PssGroup, Set<OoiCombination>> ooiCombinationMap;

    public OoiGroupMapper(Map<OoiCombination, PssGroup> ooiCombinationToPssGroupMap, Map<PssGroup, Set<OoiCombination>> ooiCombinationMap) {
        this.ooiCombinationToPssGroupMap = ooiCombinationToPssGroupMap;
        this.ooiCombinationMap = ooiCombinationMap;
    }

    public Map<OoiCombination, PssGroup> getOoiCombinationToPssGroupMap() {
        return ooiCombinationToPssGroupMap;
    }

    public Map<PssGroup, Set<OoiCombination>> getOoiCombinationMap() {
        return ooiCombinationMap;
    }
}
