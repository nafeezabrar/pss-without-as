package pss.result.presentable.printing;

import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface AdversaryOwnReportedValueCountPrintable {
    void printReportedValueCount(int reportedValueCount) throws PssException, IOException, URISyntaxException;
}
