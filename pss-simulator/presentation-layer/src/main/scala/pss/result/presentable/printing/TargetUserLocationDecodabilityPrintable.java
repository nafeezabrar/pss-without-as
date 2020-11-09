package pss.result.presentable.printing;

import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface TargetUserLocationDecodabilityPrintable {
    void printTargetUserLocationDecodability(double locationDecodability) throws PssException, IOException, URISyntaxException;
}
