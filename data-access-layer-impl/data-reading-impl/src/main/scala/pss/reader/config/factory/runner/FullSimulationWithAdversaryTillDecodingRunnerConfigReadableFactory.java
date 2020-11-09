package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.FullSimulationWithAdversaryTillDecodingRunnerConfigReadable;
import pss.reader.config.runner.JsonFullSimulationWithAdversaryTillDecodingRunnerConfigReader;

public class FullSimulationWithAdversaryTillDecodingRunnerConfigReadableFactory {
    public static FullSimulationWithAdversaryTillDecodingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonFullSimulationWithAdversaryTillDecodingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
