package pss.result.presentable.printing;

import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface AdversaryInterceptedReportCountPrintable {
    void printReportCounts(int totalReportCounts) throws PssException, IOException, URISyntaxException;
}
