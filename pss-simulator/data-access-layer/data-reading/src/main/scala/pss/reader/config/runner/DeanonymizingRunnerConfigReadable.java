package pss.reader.config.runner;

import pss.config.runner.DeanonymizingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface DeanonymizingRunnerConfigReadable {
    DeanonymizingRunnerConfig readDeanonymizingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
