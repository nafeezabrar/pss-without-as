package pss.reader.config.runner;

import pss.config.runner.PssVariableGenerationRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface PssVariableGeneratingRunnerConfigReadable {
    PssVariableGenerationRunnerConfig readPssVariableGeneratingRunnerConfig() throws ReaderException, InvalidConfigException, PssException;
}
