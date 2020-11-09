package pss.conversion.data_to_result.text;

import pss.conversion.data_to_result.AdversaryInterceptedReportToResultConvertable;
import pss.exception.PssException;
import pss.result.presentable.text.TextResult;

import java.util.ArrayList;
import java.util.List;


public class AdversaryInterceptedReportCountToTextResultConverter implements AdversaryInterceptedReportToResultConvertable<TextResult> {

    @Override
    public TextResult generateAdversaryReportCountResult(int totalReportCount) throws PssException {
        List<String> results = new ArrayList<>();
        results.add(totalReportCount + "");

        return new TextResult(results);
    }
}
