package pss.domain.utils.ooi;

import pss.data.ooi.Ooi;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.SingleOoiCombination;

import java.util.HashSet;
import java.util.Set;

public class SingleOoiCombinationMaker extends OoiCombinationMaker<SingleOoiCollection, SingleOoiCombination> {
    public SingleOoiCombinationMaker(SingleOoiCollection ooiCollection) {
        super(ooiCollection);
    }

    @Override
    public Set<SingleOoiCombination> generateOoiCombinations() {
        Set<SingleOoiCombination> ooiCombinations = new HashSet<>();
        for (Ooi ooi : ooiCollection.getOoiSet()) {
            ooiCombinations.add(new SingleOoiCombination(ooi));
        }
        return ooiCombinations;
    }

    @Override
    public SingleOoiCombination generateOoiCombination(SingleOoiCollection ooiCollection) {
        return new SingleOoiCombination(ooiCollection.getOoiSet().iterator().next());
    }
}
