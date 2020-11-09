package pss.result.presentable.printing;

import pss.decodability.EndPointDecodabilityResults;
import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface EndPointDecodabilityPrintable extends DecodabilityPrintable {
    void printDecodability(EndPointDecodabilityResults endPointDecodabilityResults) throws PssException, IOException, URISyntaxException;
}
