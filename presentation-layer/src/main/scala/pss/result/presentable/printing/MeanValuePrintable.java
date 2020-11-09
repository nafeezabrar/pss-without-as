package pss.result.presentable.printing;

import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface MeanValuePrintable<T> {
    void printValue(List<T> counts) throws PssException, IOException, URISyntaxException;
}
