package pss.domain.utils.ooi_local;

import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;

import java.util.Set;

public abstract class LocalOoiCombinationMaker<TLocalOoiCollection extends LocalOoiCollection, TLocalOoiCombination extends LocalOoiCombination> {
    protected final TLocalOoiCollection ooiCollection;

    protected LocalOoiCombinationMaker(TLocalOoiCollection ooiCollection) {
        this.ooiCollection = ooiCollection;
    }

    public abstract Set<TLocalOoiCombination> generateOoiCombinations();

    public abstract TLocalOoiCombination generateOoiCombination(TLocalOoiCollection ooiCollection);
}
