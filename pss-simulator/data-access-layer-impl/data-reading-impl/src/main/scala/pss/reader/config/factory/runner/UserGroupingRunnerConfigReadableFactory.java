package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.JsonUserGroupingRunnerConfigReader;
import pss.reader.config.runner.UserGroupingRunnerConfigReadable;

public class UserGroupingRunnerConfigReadableFactory {
    public static UserGroupingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonUserGroupingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
