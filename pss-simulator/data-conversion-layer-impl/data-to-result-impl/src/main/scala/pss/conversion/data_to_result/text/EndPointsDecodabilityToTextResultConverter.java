package pss.conversion.data_to_result.text;


import pss.conversion.data_to_result.EndPointsDecodabilityToResultConvertable;
import pss.data.decodability.DecodabilityType;
import pss.decodability.DecodabilityResult;
import pss.decodability.DecodabilityResultType;
import pss.decodability.EndPointDecodabilityResults;
import pss.decodability.LocationDecodabilityResult;
import pss.exception.PssException;
import pss.result.presentable.text.TextResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EndPointsDecodabilityToTextResultConverter implements EndPointsDecodabilityToResultConvertable<TextResult> {

    @Override
    public TextResult generateResult(EndPointDecodabilityResults decodabilityResults, DecodabilityResultType decodabilityResultType) throws PssException {
        Map<DecodabilityType, DecodabilityResult> decodabilityResultMap = decodabilityResults.getDecodabilityResultMap();

        List<String> results = new ArrayList<>();
        for (DecodabilityType decodabilityType : decodabilityResultMap.keySet()) {
            DecodabilityResult decodabilityResult = decodabilityResultMap.get(decodabilityType);
            String result = getDecodabilityString(decodabilityResult, decodabilityResultType, decodabilityType);
            results.add(result);
        }
        return new TextResult(results);
    }

    private String getDecodabilityString(DecodabilityResult decodabilityResult, DecodabilityResultType decodabilityResultType, DecodabilityType decodabilityType) {
        switch (decodabilityType) {

            case FULL:
                throw new UnsupportedOperationException();
            case PARTIAL:
                throw new UnsupportedOperationException();
            case LOCATION:
                return getLocationDecodabilityString((LocationDecodabilityResult) decodabilityResult, decodabilityResultType);
        }

        return decodabilityResult.toString();
    }

    private String getLocationDecodabilityString(LocationDecodabilityResult decodabilityResult, DecodabilityResultType decodabilityResultType) {
        switch (decodabilityResultType) {

            case DECODABILITY_VALUE:
                return decodabilityResult.toString();
            case DECODED_VALUE_COUNT:
                return decodabilityResult.getDecodedValueMap().size() + "";
        }
        throw new UnsupportedOperationException();
    }
}
