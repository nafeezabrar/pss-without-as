package pss.conversion.data_to_result.text;

import pss.conversion.data_to_result.DecodabilityResultToResultConvertable;
import pss.exception.PssException;
import pss.result.presentable.text.TextResult;

import java.util.ArrayList;
import java.util.List;


public class ApsDecodabilityResultToTextResultConverter implements DecodabilityResultToResultConvertable<TextResult> {

    @Override
    public TextResult generateReportCountResult(List<Double> decodabilities, int iteration) throws PssException {
        List<String> results = new ArrayList<>();
        results.add(iteration + "");
        for (Double decodability : decodabilities) {
            results.add(getDecodabilityTextResult(decodability));
        }
        return new TextResult(results);
    }

    private String getDecodabilityTextResult(Double decodability) {
        return decodability + "";
    }
}
