package pss.variable.generation;

import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.MultiPssVariables;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.SinglePssVariables;
import pss.data.valuemap.MultiValueMap;
import pss.data.valuemap.SingleValueMap;
import pss.data.valuemap.ValueMap;
import pss.exception.PssException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static pss.data.pss_type.PssType.PssDimensionType;

public class PssVariableGenerator implements PssVariableGenerable {
    protected final Map<Dimension, OoiGenerable> ooiGeneratorMap;
    protected final OoiValueGenerable ooiValueGenerator;

    public PssVariableGenerator(Map<Dimension, OoiGenerable> ooiGeneratorMap, OoiValueGenerable ooiValueGenerator) {
        this.ooiGeneratorMap = ooiGeneratorMap;
        this.ooiValueGenerator = ooiValueGenerator;
    }

    @Override
    public PssVariables generatePssVariables(PssType pssType) throws Exception {
        Map<Dimension, Integer> nMap = pssType.getnSet();
        Map<Dimension, Set<Ooi>> ooiMap = generateOoiSets(nMap);
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        ValueMap valueMap = ooiValueGenerator.generateValueMap(ooiMap);

        switch (pssDimensionType) {

            case SINGLE:
                return generateSinglePssVariables(ooiMap, pssType, (SingleValueMap) valueMap);
            case MULTI:
                return generateMultiPssVariables(ooiMap, (MultiValueMap) valueMap);
        }
        throw new PssException(String.format("pss dimension type %s not matched", pssDimensionType.toString()));
    }

    private PssVariables generateSinglePssVariables(Map<Dimension, Set<Ooi>> ooiMap, PssType pssType, SingleValueMap valueMap) throws PssException {
        Dimension dimension = pssType.getDimension();
        Set<Ooi> oois = ooiMap.get(dimension);
        SingleOoiCollection ooiCollection = new SingleOoiCollection(oois);
        return new SinglePssVariables(ooiCollection, valueMap);
    }

    private PssVariables generateMultiPssVariables(Map<Dimension, Set<Ooi>> ooiMap, MultiValueMap multiValueMap) {
        MultiOoiCollection multiOoiCollection = new MultiOoiCollection(ooiMap);
        return new MultiPssVariables(multiOoiCollection, multiValueMap);
    }

    private Map<Dimension, Set<Ooi>> generateOoiSets(Map<Dimension, Integer> nMap) {
        Map<Dimension, Set<Ooi>> ooiMap = new HashMap<>();
        int startOoiId = 1;
        for (Dimension dimension : nMap.keySet()) {
            OoiGenerable ooiGenerator = ooiGeneratorMap.get(dimension);
            int n = nMap.get(dimension);
            Set<Ooi> oois = ooiGenerator.generateOoi(n, startOoiId);
            ooiMap.put(dimension, oois);
            startOoiId += n + 1;
        }
        return ooiMap;
    }
}
