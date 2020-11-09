package pss.result.presentable.printing;

import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ResultSummaryPrintable {
    void printResultSummary() throws PssException, IOException, URISyntaxException;
}
