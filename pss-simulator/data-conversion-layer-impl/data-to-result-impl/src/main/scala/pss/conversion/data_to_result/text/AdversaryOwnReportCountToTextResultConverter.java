package pss.conversion.data_to_result.text;

import pss.conversion.data_to_result.AdversaryOwnReportToResultConvertable;
import pss.exception.PssException;
import pss.result.presentable.text.TextResult;

import java.util.ArrayList;
import java.util.List;


public class AdversaryOwnReportCountToTextResultConverter implements AdversaryOwnReportToResultConvertable<TextResult> {

    @Override
    public TextResult generateObservedReportResult(int totalReportCount) throws PssException {
        List<String> results = new ArrayList<>();
        results.add(totalReportCount + "");
        return new TextResult(results);
    }
}
