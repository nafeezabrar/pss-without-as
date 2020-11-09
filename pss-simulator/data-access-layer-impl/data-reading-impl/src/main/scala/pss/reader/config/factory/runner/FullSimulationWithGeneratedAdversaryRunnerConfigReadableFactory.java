package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.FullSimulationWithGeneratedAdversaryRunnerConfigReadable;
import pss.reader.config.runner.JsonFullSimulationWithGeneratedAdversaryRunnerConfigReader;

public class FullSimulationWithGeneratedAdversaryRunnerConfigReadableFactory {
    public static FullSimulationWithGeneratedAdversaryRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonFullSimulationWithGeneratedAdversaryRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
