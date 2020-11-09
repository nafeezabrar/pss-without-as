package pss.anonymization.dgas;

import pss.anonymization.CombinationTableOptimalUpdatable;
import pss.data.combination_table.DecodedMapTable;
import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;

public abstract class DecodedMapTableOptimalUpdater<TLocalOOiCombination extends LocalOoiCombination, TLocalOoiCollection extends LocalOoiCollection> implements CombinationTableOptimalUpdatable<TLocalOOiCombination, TLocalOoiCollection> {
    protected final DecodedMapTable<TLocalOOiCombination> decodedMapTable;
    protected final TLocalOoiCollection localOoiCollection;

    protected DecodedMapTableOptimalUpdater(DecodedMapTable<TLocalOOiCombination> decodedMapTable, TLocalOoiCollection localOoiCollection) {
        this.decodedMapTable = decodedMapTable;
        this.localOoiCollection = localOoiCollection;
    }
}
