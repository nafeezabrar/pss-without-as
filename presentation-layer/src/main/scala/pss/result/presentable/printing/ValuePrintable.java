package pss.result.presentable.printing;

import pss.exception.PssException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ValuePrintable<T> {
    void printValue(T count) throws PssException, IOException, URISyntaxException;
}
