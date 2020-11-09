package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.FullSimulationTillDecodingRunnerConfigReadable;
import pss.reader.config.runner.JsonFullSimulationTillDecodingRunnerConfigReader;

public class FullSimulationTillDecodingRunnerConfigReadableFactory {
    public static FullSimulationTillDecodingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonFullSimulationTillDecodingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
