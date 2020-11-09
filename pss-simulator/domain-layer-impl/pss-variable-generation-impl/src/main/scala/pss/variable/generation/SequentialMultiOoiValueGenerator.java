package pss.variable.generation;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.valuemap.MultiValueMap;
import pss.data.valuemap.Value;
import pss.domain.utils.ooi.MultiOoiCombinationMaker;

import java.util.*;

public class SequentialMultiOoiValueGenerator implements OoiValueGenerable<MultiValueMap> {
    @Override
    public MultiValueMap generateValueMap(Map<Dimension, Set<Ooi>> ooiMap) {
        Set<MultiOoiCombination> multiOoiCombinations = generateMultiOoiCombinations(ooiMap);
        Map<MultiOoiCombination, Value> ooiValues = new HashMap<>();
        List<MultiOoiCombination> orderedCombinations = new ArrayList<>();
        orderedCombinations.addAll(multiOoiCombinations);
        orderedCombinations.sort(new Comparator<MultiOoiCombination>() {
            @Override
            public int compare(MultiOoiCombination c1, MultiOoiCombination c2) {
                Map<Dimension, Ooi> ooiMap1 = c1.getOoiMap();
                Map<Dimension, Ooi> ooiMap2 = c2.getOoiMap();
                for (Dimension dimension : ooiMap1.keySet()) {
                    int c1Id = ooiMap1.get(dimension).getId();
                    int c2Id = ooiMap2.get(dimension).getId();
                    int compare = Integer.compare(c1Id, c2Id);
                    if (compare != 0)
                        return compare;
                }
                return 0;
            }
        });
        int value = 1;
        for (MultiOoiCombination combination : orderedCombinations) {
            ooiValues.put(combination, new Value(value));
            value++;
        }
        return new MultiValueMap(ooiValues);
    }

    private Set<MultiOoiCombination> generateMultiOoiCombinations(Map<Dimension, Set<Ooi>> ooiMap) {
        MultiOoiCollection ooiCollection = new MultiOoiCollection(ooiMap);
        MultiOoiCombinationMaker ooiUtil = new MultiOoiCombinationMaker(ooiCollection);
        Set<MultiOoiCombination> multiOoiCombinations = ooiUtil.generateOoiCombinations();
        return multiOoiCombinations;
    }
}
