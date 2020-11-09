package pss.conversion.data_to_result.tabular;

import pss.conversion.data_to_result.PssVariableToResultConvertable;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.result.presentable.tabular.TabularResult;

public class PssVariableToTabularResultConverter implements PssVariableToResultConvertable {

    @Override
    public TabularResult generateResult(PssVariables pssVariables, PssType pssType) throws PssException {
        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                SinglePssVariableToTabularResultConverter singleResultConverter = new SinglePssVariableToTabularResultConverter(pssVariables, pssType);
                return singleResultConverter.generateSinglePssVariableResult();
            case MULTI:
                MultiPssVariableToTabularResultConverter multiResultConverter = new MultiPssVariableToTabularResultConverter(pssVariables, pssType);
                return multiResultConverter.generateMultiPssVariableResult();
        }
        throw new PssException("pss typeKey not recognized while generating psv result");
    }
}
