package pss.reader.config.runner;

import pss.config.runner.FullSimulationWithAdversaryRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface FullSimulationWithAdversaryRunnerConfigReadable {
    FullSimulationWithAdversaryRunnerConfig generateFullSimulationWithAdversaryRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
