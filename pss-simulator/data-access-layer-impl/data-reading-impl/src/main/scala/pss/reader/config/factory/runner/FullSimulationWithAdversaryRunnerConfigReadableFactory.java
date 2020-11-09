package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.FullSimulationWithAdversaryRunnerConfigReadable;
import pss.reader.config.runner.JsonFullSimulationWithAdversaryRunnerConfigReader;

public class FullSimulationWithAdversaryRunnerConfigReadableFactory {
    public static FullSimulationWithAdversaryRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonFullSimulationWithAdversaryRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
