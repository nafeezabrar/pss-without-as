package pss.conversion.data_to_result.text;

import pss.conversion.data_to_result.TargetUserLocationDecodabilityToResultConvertable;
import pss.exception.PssException;
import pss.result.presentable.text.TextResult;

import java.util.ArrayList;
import java.util.List;


public class TargetUserLocationDecodabilityToTextResultConverter implements TargetUserLocationDecodabilityToResultConvertable<TextResult> {

    @Override
    public TextResult generateTargetUserLocationDecodabilityResult(double locationDecodability) throws PssException {
        List<String> results = new ArrayList<>();
        results.add(locationDecodability + "");

        return new TextResult(results);
    }
}
