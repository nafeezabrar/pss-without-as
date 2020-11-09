package pss.result.presentable.printing;

import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface ObservedReportByGroupPrintable {
    public void printObservedReportByGroup(Map<PssGroup, Integer> reportCountMap) throws PssException, IOException, URISyntaxException;
}
