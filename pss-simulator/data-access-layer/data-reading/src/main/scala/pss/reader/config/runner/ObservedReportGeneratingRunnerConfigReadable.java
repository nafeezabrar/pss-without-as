package pss.reader.config.runner;

import pss.config.runner.ObservedReportGeneratingRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;

public interface ObservedReportGeneratingRunnerConfigReadable {
    ObservedReportGeneratingRunnerConfig generateObservedReportGeneratingRunnerConfig() throws InvalidConfigException, ReaderException, PssException;
}
