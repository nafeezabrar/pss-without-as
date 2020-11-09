package pss.reader.config.runner;

import pss.config.runner.FullSimulationTillDecodingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface FullSimulationTillDecodingRunnerConfigReadable {
    FullSimulationTillDecodingRunnerConfig generateFullSimulationTillDecodingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
