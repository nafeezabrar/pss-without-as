package pss.deanonymization.dgas.decodingMapTable.updating;

import pss.data.combination_table.DecodedMapTable;
import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;

public class DgasDecodingTableUpdater<TLocalOoiCombination extends LocalOoiCombination, TLocalOoiCollection extends LocalOoiCollection> {
    protected final DecodedMapTable<TLocalOoiCombination> decodedMapTable;
    protected final TLocalOoiCollection localOoiCollection;

    public DgasDecodingTableUpdater(DecodedMapTable<TLocalOoiCombination> decodedMapTable, TLocalOoiCollection localOoiCollection) {
        this.decodedMapTable = decodedMapTable;
        this.localOoiCollection = localOoiCollection;
    }
}
