package pss.domain.utils;

import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pssvariable.SinglePssVariables;

import java.util.Map;

public class SinglePssUtils implements PssUtils<SinglePssVariables, SingleLocalOoiCombination, SingleLocalOoiMapper> {
    @Override
    public int countTotalOois(SinglePssVariables pssVariables) {
        SingleOoiCollection ooiCollection = pssVariables.getOoiCollection();
        return ooiCollection.getOoiSet().size();
    }

    @Override
    public SingleOoiCombination getOoiCombination(SingleLocalOoiCombination localOoiCombination, SingleLocalOoiMapper localOoiMapper) {
        Map<Integer, Ooi> idToOoiMap = localOoiMapper.getIdToOoiMap();
        int ooiId = localOoiCombination.getLocalOoi();
        Ooi ooi = idToOoiMap.get(ooiId);
        return new SingleOoiCombination(ooi);
    }
}
