package pss.domain.utils;

import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.pssvariable.MultiPssVariables;

import java.util.Map;
import java.util.Set;

public class MultiPssUtils implements PssUtils<MultiPssVariables, MultiLocalOoiCombination, MultiLocalOoiMapper> {
    @Override
    public int countTotalOois(MultiPssVariables pssVariables) {
        MultiOoiCollection ooiCollection = pssVariables.getOoiCollection();
        Map<Dimension, Set<Ooi>> ooiSetMap = ooiCollection.getOoiSetMap();
        if (ooiSetMap.size() == 0) return 0;
        int totalOoi = 1;
        for (Dimension dimension : ooiSetMap.keySet()) {
            Set<Ooi> oois = ooiSetMap.get(dimension);
            totalOoi *= oois.size();
        }
        return totalOoi;
    }

    @Override
    public SingleOoiCombination getOoiCombination(MultiLocalOoiCombination localOoiCombination, MultiLocalOoiMapper localOoiMapper) {
        throw new UnsupportedOperationException("not ipml");
    }
}
