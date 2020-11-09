package pss.data.pssvariable;

import pss.data.ooi.collection.OoiCollection;
import pss.data.ooi.combination.OoiCombination;
import pss.data.valuemap.ValueMap;

public abstract class PssVariables<TValueMap extends ValueMap<TOoiCombination>,
        TOoiCollection extends OoiCollection, TOoiCombination extends OoiCombination> {

    protected final TOoiCollection ooiCollection;
    protected final TValueMap valueMap;

    protected PssVariables(TOoiCollection ooiCollection, TValueMap valueMap) {
        this.ooiCollection = ooiCollection;
        this.valueMap = valueMap;
    }

    public TOoiCollection getOoiCollection() {
        return ooiCollection;
    }

    public TValueMap getValueMap() {
        return valueMap;
    }
}
