package pss.result.presentable.printing;

import pss.data.user.User;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface CountByUserPrintable {
    void printReportCount(Map<User, Integer> reportCountMap) throws PssException, IOException, URISyntaxException;
}
