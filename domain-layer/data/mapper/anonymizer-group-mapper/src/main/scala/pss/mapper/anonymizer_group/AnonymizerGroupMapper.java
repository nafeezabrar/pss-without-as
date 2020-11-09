package pss.mapper.anonymizer_group;


import pss.anonymization.Anonymizable;
import pss.data.pssvariable.group.PssGroup;

import java.util.Map;

public class AnonymizerGroupMapper {
    protected final Map<PssGroup, Anonymizable> anonymizerMap;

    public AnonymizerGroupMapper(Map<PssGroup, Anonymizable> anonymizerMap) {
        this.anonymizerMap = anonymizerMap;
    }

    public Anonymizable getAnoymizer(PssGroup pssGroup) {
        return anonymizerMap.get(pssGroup);
    }
}
