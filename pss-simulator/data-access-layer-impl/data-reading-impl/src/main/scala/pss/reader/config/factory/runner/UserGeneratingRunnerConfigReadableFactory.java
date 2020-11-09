package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.JsonUserGeneratingRunnerConfigReader;
import pss.reader.config.runner.UserGeneratingRunnerConfigReadable;

public class UserGeneratingRunnerConfigReadableFactory {
    public static UserGeneratingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonUserGeneratingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
