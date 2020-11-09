package pss.mapper.deanonymizer_group;

import pss.data.pssvariable.group.PssGroup;
import pss.deanonymization.Deanonymizable;

import java.util.Map;

public class DeanonymizerGroupMapper {
    protected final Map<PssGroup, Deanonymizable> deanonymizerMap;

    public DeanonymizerGroupMapper(Map<PssGroup, Deanonymizable> deanonymizerMap) {
        this.deanonymizerMap = deanonymizerMap;
    }

    public Deanonymizable getDeanonymizer(PssGroup pssGroup) {
        return deanonymizerMap.get(pssGroup);
    }

    public Map<PssGroup, Deanonymizable> getDeanonymizerMap() {
        return deanonymizerMap;
    }
}
