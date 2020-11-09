package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.JsonObservedReportGeneratingRunnerConfigReader;
import pss.reader.config.runner.ObservedReportGeneratingRunnerConfigReadable;

public class ObservedReportGeneratingRunnerConfigReadableFactory {
    public static ObservedReportGeneratingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonObservedReportGeneratingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
