package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.JsonPssVariableGeneratingRunnerConfigReader;
import pss.reader.config.runner.PssVariableGeneratingRunnerConfigReadable;

public class PssVariableGeneratingRunnerConfigReadableFactory {
    public static PssVariableGeneratingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonPssVariableGeneratingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
