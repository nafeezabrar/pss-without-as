package pss.reader.config.runner;

import pss.config.runner.FullSimulationWithAdversaryTillDecodingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface FullSimulationWithAdversaryTillDecodingRunnerConfigReadable {
    FullSimulationWithAdversaryTillDecodingRunnerConfig generateFullSimulationWithAdversaryTillDecodingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
