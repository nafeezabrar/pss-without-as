package pss.reader.config.runner;

import pss.config.runner.ResultFilteringRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface ResultFilteringRunnerConfigReadable {
    ResultFilteringRunnerConfig readResultFilteringRunnerConfig() throws ReaderException, InvalidConfigException, PssException;
}
