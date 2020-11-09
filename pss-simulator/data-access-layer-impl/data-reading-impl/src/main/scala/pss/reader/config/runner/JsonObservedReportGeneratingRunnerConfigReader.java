package pss.reader.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.ObservedReportGeneratingRunnerConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.JsonPostResultProcessConfigReader;
import pss.reader.config.readers.JsonSeedReader;
import pss.reader.config.readers.JsonUserGroupingConfigReader;

import static pss.reader.config.readers.JsonObservedReportGenerationConfigReader.readObservedReportGenerationConfig;
import static pss.reader.config.readers.JsonPssGroupingConfigReader.readPssGroupingConfig;
import static pss.reader.config.readers.JsonPssTypeReader.readPssType;
import static pss.reader.config.readers.JsonPssVariableGeneratingConfigReader.readPssVariableGenerationConfig;
import static pss.reader.config.readers.JsonUserGenerationConfigReader.readUserGenerationConfig;

public class JsonObservedReportGeneratingRunnerConfigReader extends JsonConfigReader implements ObservedReportGeneratingRunnerConfigReadable {
    public JsonObservedReportGeneratingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public ObservedReportGeneratingRunnerConfig generateObservedReportGeneratingRunnerConfig() throws InvalidConfigException, ReaderException, PssException {
        PssType pssType = readPssType(jsonFileReader);
        long seed = JsonSeedReader.readSeed(jsonFileReader);
        PssVariableGenerationConfig pssVariableGenerationConfig = readPssVariableGenerationConfig(jsonFileReader, pssType);
        PssGroupingConfig pssGroupingConfig = readPssGroupingConfig(jsonFileReader, pssType);
        UserGenerationConfig userGenerationConfig = readUserGenerationConfig(jsonFileReader);
        UserGroupingConfig userGroupingConfig = JsonUserGroupingConfigReader.readUserGroupingConfig(jsonFileReader);
        ObservedReportGenerationConfig observedReportGenerationConfig = readObservedReportGenerationConfig(jsonFileReader);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new ObservedReportGeneratingRunnerConfig(postResultProcessConfig, pssType, pssVariableGenerationConfig, pssGroupingConfig, userGenerationConfig, userGroupingConfig, observedReportGenerationConfig);
    }
}
