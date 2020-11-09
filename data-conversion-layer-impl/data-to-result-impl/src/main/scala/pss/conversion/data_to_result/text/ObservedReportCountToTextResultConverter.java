package pss.conversion.data_to_result.text;

import pss.conversion.data_to_result.ObservedReportCountToResultConvertable;
import pss.data.user.User;
import pss.exception.PssException;
import pss.result.presentable.text.TextResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ObservedReportCountToTextResultConverter implements ObservedReportCountToResultConvertable<TextResult> {

    @Override
    public TextResult generateReportCountResult(Map<User, Integer> reportCountMap) throws PssException {
        List<String> results = new ArrayList<>();
        for (User user : reportCountMap.keySet()) {
            results.add(getTextResult(user, reportCountMap.get(user)));
        }
        return new TextResult(results);
    }

    private String getTextResult(User user, int reportCount) {
        return reportCount + "";
    }
}
