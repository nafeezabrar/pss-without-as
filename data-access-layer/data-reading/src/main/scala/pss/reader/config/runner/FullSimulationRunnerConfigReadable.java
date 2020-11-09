package pss.reader.config.runner;

import pss.config.runner.FullSimulationRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface FullSimulationRunnerConfigReadable {
    FullSimulationRunnerConfig generateFullSimulationRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
