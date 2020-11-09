package pss.reader.config.runner;

import pss.config.runner.UserGroupingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface UserGroupingRunnerConfigReadable {
    UserGroupingRunnerConfig readUserGroupingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
