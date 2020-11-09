package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.AnonymizingRunnerConfigReadable;
import pss.reader.config.runner.JsonAnonymizingRunnerConfigReader;

public class AnonymizingRunnerConfigReadableFactory {
    public static AnonymizingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonAnonymizingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
