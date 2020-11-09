package pss.reader.config.factory.runner;

import pss.ReadingSourceType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.runner.FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfigReadable;
import pss.reader.config.runner.JsonFullSimulationWithGeneratedAdversaryTillDecodingRunnerConfigReader;

public class FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfigReadableFactory {
    public static FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfigReadable generateRunnerConfigReader(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException {
        switch (readingSourceType) {
            case JSON:
                return new JsonFullSimulationWithGeneratedAdversaryTillDecodingRunnerConfigReader(fileName);
        }
        throw new InvalidConfigException(String.format("Reader is not implemented for %s", readingSourceType));
    }
}
