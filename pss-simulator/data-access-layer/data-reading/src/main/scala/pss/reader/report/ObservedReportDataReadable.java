package pss.reader.report;

import pss.exception.ReaderException;
import pss.report.observed.ObservedReportFromFile;

import java.util.List;

public interface ObservedReportDataReadable {
    List<ObservedReportFromFile> generateObservedReportData() throws ReaderException;
}
