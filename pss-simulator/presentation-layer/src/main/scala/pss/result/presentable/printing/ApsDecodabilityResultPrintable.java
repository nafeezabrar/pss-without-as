package pss.result.presentable.printing;

import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ApsDecodabilityResultPrintable {
    void printDecodability(List<Double> decodabilities) throws PssException, IOException, URISyntaxException;
}
