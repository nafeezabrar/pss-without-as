package pss.anonymization.dgas;

import pss.anonymization.AnonymizedCollectionGenerable;
import pss.data.anonymity.MultiAnonymity;
import pss.data.combination_table.DecodedMapTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;

import java.util.Set;

public class MultiAnonymizedCollectionGenerator implements AnonymizedCollectionGenerable<MultiLocalOoiCombination, MultiAnonymity, MultiLocalOoiCollection> {
    protected final DecodedMapTable<SingleLocalOoiCombination> decodedMapTable;

    public MultiAnonymizedCollectionGenerator(DecodedMapTable<SingleLocalOoiCombination> decodedMapTable) {
        this.decodedMapTable = decodedMapTable;
    }

    @Override
    public Set<MultiLocalOoiCollection> generatePossibleAnonymization(MultiLocalOoiCombination localOoiCombination, MultiAnonymity anonymity) {

        throw new UnsupportedOperationException("Not implemented");
    }
}
