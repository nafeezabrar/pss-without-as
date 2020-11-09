package pss.data.pssvariable;

import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.valuemap.MultiValueMap;

public class MultiPssVariables extends PssVariables<MultiValueMap, MultiOoiCollection, MultiOoiCombination> {

    public MultiPssVariables(MultiOoiCollection ooiCollection, MultiValueMap valueMap) {
        super(ooiCollection, valueMap);
    }
}
