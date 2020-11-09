package pss.domain.utils.ooi;

import pss.data.ooi.collection.OoiCollection;
import pss.data.ooi.combination.OoiCombination;

import java.util.Set;

public abstract class OoiCombinationMaker<TOoiCollection extends OoiCollection, TOoiCombination extends OoiCombination> {
    protected final TOoiCollection ooiCollection;

    protected OoiCombinationMaker(TOoiCollection ooiCollection) {
        this.ooiCollection = ooiCollection;
    }

    public abstract Set<TOoiCombination> generateOoiCombinations();

    public abstract TOoiCombination generateOoiCombination(TOoiCollection ooiCollection);
}
