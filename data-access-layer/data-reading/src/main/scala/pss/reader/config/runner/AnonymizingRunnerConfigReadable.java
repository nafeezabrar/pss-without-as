package pss.reader.config.runner;

import pss.config.runner.AnonymizingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface AnonymizingRunnerConfigReadable {
    AnonymizingRunnerConfig readAnonymizingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
