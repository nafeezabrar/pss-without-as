package pss.data.pssvariable;

import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.valuemap.SingleValueMap;


public class SinglePssVariables extends PssVariables<SingleValueMap, SingleOoiCollection, SingleOoiCombination> {

    public SinglePssVariables(SingleOoiCollection ooiCollection, SingleValueMap valueMap) {
        super(ooiCollection, valueMap);
    }
}
