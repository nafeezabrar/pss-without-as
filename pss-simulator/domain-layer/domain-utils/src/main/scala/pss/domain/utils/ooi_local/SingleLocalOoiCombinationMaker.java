package pss.domain.utils.ooi_local;


import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;

import java.util.HashSet;
import java.util.Set;

public class SingleLocalOoiCombinationMaker extends LocalOoiCombinationMaker<SingleLocalOoiCollection, SingleLocalOoiCombination> {
    public SingleLocalOoiCombinationMaker(SingleLocalOoiCollection ooiCollection) {
        super(ooiCollection);
    }

    @Override
    public Set<SingleLocalOoiCombination> generateOoiCombinations() {
        Set<SingleLocalOoiCombination> ooiCombinations = new HashSet<>();
        for (Integer ooiId : ooiCollection.getLocalOoiSet()) {
            ooiCombinations.add(new SingleLocalOoiCombination(ooiId));
        }
        return ooiCombinations;
    }

    @Override
    public SingleLocalOoiCombination generateOoiCombination(SingleLocalOoiCollection ooiCollection) {
        return new SingleLocalOoiCombination(ooiCollection.getLocalOoiSet().iterator().next());
    }
}
