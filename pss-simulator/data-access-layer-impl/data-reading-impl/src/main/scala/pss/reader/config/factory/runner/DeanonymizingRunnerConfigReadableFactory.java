package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.DeanonymizingRunnerConfigReadable;
import pss.reader.config.runner.JsonDeanonymizingRunnerConfigReader;

public class DeanonymizingRunnerConfigReadableFactory {
    public static DeanonymizingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonDeanonymizingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
