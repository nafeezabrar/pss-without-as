package pss.domain.utils.ooi;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.combination.MultiOoiCombination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiOoiCombinationMaker extends OoiCombinationMaker<MultiOoiCollection, MultiOoiCombination> {
    private final int totalDimensions;
    private Map<Dimension, Integer> ooiIdMaps;
    private Set<MultiOoiCombination> ooiCombinations;
    private Dimension[] dimensions;

    public MultiOoiCombinationMaker(MultiOoiCollection ooiCollection) {
        super(ooiCollection);
        Map<Dimension, Set<Ooi>> ooiSetMap = ooiCollection.getOoiSetMap();
        Set<Dimension> dimensionSet = ooiSetMap.keySet();
        this.totalDimensions = ooiSetMap.size();
        this.dimensions = dimensionSet.toArray(new Dimension[totalDimensions]);
    }

    public Set<MultiOoiCombination> generateOoiCombinations() {
        this.ooiCombinations = new HashSet<>();
        generateOoiCombinationsRecursively(0, new HashMap<>());
        return ooiCombinations;
    }

    @Override
    public MultiOoiCombination generateOoiCombination(MultiOoiCollection ooiCollection) {
        throw new UnsupportedOperationException("MultiOoiCombinationMaker generateOoiCombination not implemented");
    }

    private void generateOoiCombinationsRecursively(int dimensionIndex, Map<Dimension, Ooi> ooiMap) {
        if (dimensionIndex == totalDimensions) {
            ooiCombinations.add(new MultiOoiCombination(ooiMap));
            return;
        }
        Dimension dimension = dimensions[dimensionIndex];
        Set<Ooi> ooiIds = ooiCollection.getOoiSetMap().get(dimension);
        for (Ooi Ooi : ooiIds) {
            Map<Dimension, Ooi> newIdMap = new HashMap<>();
            for (Map.Entry<Dimension, Ooi> entry : ooiMap.entrySet()) {
                newIdMap.put(entry.getKey(), entry.getValue());
            }
            newIdMap.put(dimension, Ooi);
            generateOoiCombinationsRecursively(dimensionIndex + 1, newIdMap);
        }
    }
}
