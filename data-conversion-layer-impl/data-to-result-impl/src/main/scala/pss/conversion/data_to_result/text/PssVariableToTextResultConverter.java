package pss.conversion.data_to_result.text;


import pss.conversion.data_to_result.PssVariableToResultConvertable;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.MultiPssVariables;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.SinglePssVariables;
import pss.data.valuemap.SingleValueMap;
import pss.exception.PssException;
import pss.result.presentable.text.TextResult;

import java.util.ArrayList;
import java.util.List;

public class PssVariableToTextResultConverter implements PssVariableToResultConvertable {
    @Override
    public TextResult generateResult(PssVariables pssVariables, PssType pssType) throws PssException {

        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return generateSinglePssVariableResult((SinglePssVariables) pssVariables);
            case MULTI:
                return generateMultiPssVariableResult((MultiPssVariables) pssVariables, pssType);
        }
        throw new PssException("Pss Variable cannot be converted to text result");
    }

    private TextResult generateSinglePssVariableResult(SinglePssVariables pssVariables) {
        List<String> results = new ArrayList<>();
        results.add("Pss variables : ");
        SingleOoiCollection ooiCollection = pssVariables.getOoiCollection();
        ArrayList<Ooi> orderedOoiList = new ArrayList<>(ooiCollection.getOoiSet());
        orderedOoiList.sort((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        SingleValueMap valueMap = pssVariables.getValueMap();
        for (Ooi ooi : orderedOoiList) {
            int value = valueMap.getValue(new SingleOoiCombination(ooi)).getIntValue();
            String valueMapString = String.format("%d (%s) = %d", ooi.getId(), ooi.getName(), value);
            results.add(valueMapString);
        }
        return new TextResult(results);
    }

    private TextResult generateMultiPssVariableResult(MultiPssVariables pssVariables, PssType pssType) {
        throw new UnsupportedOperationException("Multi pss variable result save in text not implemented");
    }
}
