package pss.decodability.calculation;


import pss.data.decodability.DecodabilityType;
import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.Value;
import pss.data.valuemap.ValueMap;
import pss.deanonymization.Deanonymizable;
import pss.decodability.FullDecodabilityResult;
import pss.decodability.calculation.aps.EndPointDecodabilityCalculable;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;

import java.util.HashMap;
import java.util.Map;

public class EndPointApsFullDecodabilityCalculator implements EndPointDecodabilityCalculable<FullDecodabilityResult> {
    protected final DeanonymizerGroupMapper deanonymizerGroupMapper;
    protected final OoiUserGroupMappable ooiUserGroupMapper;
    protected final ValueMap valueMap;

    public EndPointApsFullDecodabilityCalculator(DeanonymizerGroupMapper deanonymizerGroupMapper, OoiUserGroupMappable ooiUserGroupMapper, ValueMap valueMap) {
        this.deanonymizerGroupMapper = deanonymizerGroupMapper;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
        this.valueMap = valueMap;
    }

    @Override
    public FullDecodabilityResult calculateDecodability() {
        int totalDecoded = 0;
        Map<OoiCombination, Value> allDecodedValueMap = new HashMap<>();
        Map<PssGroup, Deanonymizable> deanonymizerMap = deanonymizerGroupMapper.getDeanonymizerMap();
        for (PssGroup pssGroup : deanonymizerMap.keySet()) {
            Deanonymizable deanonymizer = deanonymizerMap.get(pssGroup);
            Map<LocalOoiCombination, Value> decodedValueMap = deanonymizer.getDecodedValueMap();
            for (LocalOoiCombination localOoiCombination : decodedValueMap.keySet()) {
                OoiCombination ooiCombination = ooiUserGroupMapper.getOoiCombination(localOoiCombination, pssGroup);
                if (valueMap.getValue(ooiCombination) == decodedValueMap.get(localOoiCombination)) {
                    totalDecoded++;
                    allDecodedValueMap.put(ooiCombination, valueMap.getValue(ooiCombination));
                }
            }
        }
        double decodability = (double) totalDecoded / valueMap.getValues().size();
        return new FullDecodabilityResult(decodability, allDecodedValueMap);

    }

    @Override
    public DecodabilityType getDecodabilityType() {
        return DecodabilityType.FULL;
    }
}
