package pss.reader.config.runner;

import pss.config.runner.FullSimulationWithGeneratedAdversaryRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface FullSimulationWithGeneratedAdversaryRunnerConfigReadable {
    FullSimulationWithGeneratedAdversaryRunnerConfig generateFullSimulationWithGeneratedAdversaryRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
