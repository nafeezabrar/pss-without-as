package pss.reader.config.runner;

import pss.config.runner.UserGeneratingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface UserGeneratingRunnerConfigReadable {
    UserGeneratingRunnerConfig readUserGeneratingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
