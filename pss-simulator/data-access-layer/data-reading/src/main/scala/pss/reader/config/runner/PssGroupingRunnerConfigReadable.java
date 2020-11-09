package pss.reader.config.runner;

import pss.config.runner.PssGroupingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface PssGroupingRunnerConfigReadable {
    PssGroupingRunnerConfig generatePssGroupingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
