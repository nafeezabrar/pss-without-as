package pss.variable.generation;

import com.github.javafaker.Faker;
import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pss_type.PssType.PssDimensionType;
import pss.data.pssvariable.MultiPssVariables;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.SinglePssVariables;
import pss.data.valuemap.MultiValueMap;
import pss.data.valuemap.SingleValueMap;
import pss.data.valuemap.Value;
import pss.domain.utils.ooi.MultiOoiCombinationMaker;
import pss.exception.PssException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RandomPssVariableGenerator implements PssVariableGenerable {

    protected final PssType pssType;
    private final Faker faker;

    public RandomPssVariableGenerator(Faker faker, PssType pssType) {
        this.faker = faker;
        this.pssType = pssType;
    }

    @Override
    public PssVariables generatePssVariables(PssType pssType) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return generateSingleDimensionalOoi(pssType.getN());
            case MULTI:
                return generateMultiDimensionalOoi(pssType.getDimensionSet(), pssType.getnSet());
        }
        throw new PssException("Invalid Pss Dimension type");
    }

    public SinglePssVariables generateSingleDimensionalOoi(int n) {
        Set<Ooi> oois = new HashSet<>();
        Map<SingleOoiCombination, Value> ooiValues = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int ooiId = i + 1;
            Ooi ooi = new Ooi(ooiId, faker.commerce().material());
            oois.add(ooi);
            SingleOoiCombination ooiCombination = new SingleOoiCombination(ooi);
            ooiValues.put(ooiCombination, new Value(i));
        }
        SingleOoiCollection ooiCollection = new SingleOoiCollection(oois);
        SingleValueMap valueMap = new SingleValueMap(ooiValues);
        return new SinglePssVariables(ooiCollection, valueMap);
    }

    public MultiPssVariables generateMultiDimensionalOoi(Set<Dimension> dimensions, Map<Dimension, Integer> NMap) {

        Map<Dimension, Set<Ooi>> ooiMap = new HashMap<>();
        for (Dimension dimension : dimensions) {
            Set<Ooi> oois = new HashSet<>();
            for (int i = 1; i <= NMap.get(dimension); i++) {
                Ooi ooi = new Ooi(i, faker.commerce().material());
                oois.add(ooi);
            }
            ooiMap.put(dimension, oois);
        }

        MultiOoiCollection ooiCollection = new MultiOoiCollection(ooiMap);
        MultiOoiCombinationMaker ooiUtil = new MultiOoiCombinationMaker(ooiCollection);
        Set<MultiOoiCombination> multiOoiCombinations = ooiUtil.generateOoiCombinations();
        Map<MultiOoiCombination, Value> ooiValues = new HashMap<>();
        int value = 1;
        for (MultiOoiCombination combination : multiOoiCombinations) {
            ooiValues.put(combination, new Value(value));
            value++;
        }
        MultiValueMap multiValueMap = new MultiValueMap(ooiValues);
        return new MultiPssVariables(ooiCollection, multiValueMap);
    }

}
