package pss.reader.config.runner;

import pss.config.runner.RunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

import java.util.Map;

public interface RunnerConfigReadable<TRunnerConfig extends RunnerConfig> {
    TRunnerConfig readPssRunnerConfig() throws ReaderException, InvalidConfigException, PssException;

    Map<String, TRunnerConfig> readPssRunnerConfigs() throws ReaderException, InvalidConfigException, PssException;
}
