package pss.domain.utils.ooi_local;

import pss.data.dimension.Dimension;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiLocalOoiCombinationMaker extends LocalOoiCombinationMaker<MultiLocalOoiCollection, MultiLocalOoiCombination> {
    private final int totalDimensions;
    private Map<Dimension, Integer> ooiIdMaps;
    private Set<MultiLocalOoiCombination> ooiCombinations;
    private Dimension[] dimensions;

    public MultiLocalOoiCombinationMaker(MultiLocalOoiCollection ooiCollection) {
        super(ooiCollection);
        Map<Dimension, Set<Integer>> localOoiSetMap = ooiCollection.getLocalOoiMap();
        Set<Dimension> dimensionSet = localOoiSetMap.keySet();
        this.totalDimensions = localOoiSetMap.size();
        this.dimensions = dimensionSet.toArray(new Dimension[totalDimensions]);
    }

    public Set<MultiLocalOoiCombination> generateOoiCombinations() {
        this.ooiCombinations = new HashSet<>();
        generateOoiCombinationsRecursively(0, new HashMap<>());
        return ooiCombinations;
    }

    @Override
    public MultiLocalOoiCombination generateOoiCombination(MultiLocalOoiCollection ooiCollection) {
        throw new UnsupportedOperationException("MultiLocalOoiCombinationMaker generateOoiCombination not implemented");
    }

    private void generateOoiCombinationsRecursively(int dimensionIndex, Map<Dimension, Integer> ooiMap) {
        if (dimensionIndex == totalDimensions) {
            ooiCombinations.add(new MultiLocalOoiCombination(ooiMap));
            return;
        }
        Dimension dimension = dimensions[dimensionIndex];
        Set<Integer> ooiIds = ooiCollection.getLocalOoiMap().get(dimension);
        for (Integer ooiId : ooiIds) {
            Map<Dimension, Integer> newIdMap = new HashMap<>();
            for (Map.Entry<Dimension, Integer> entry : ooiMap.entrySet()) {
                newIdMap.put(entry.getKey(), entry.getValue());
            }
            newIdMap.put(dimension, ooiId);
            generateOoiCombinationsRecursively(dimensionIndex + 1, newIdMap);
        }
    }
}
