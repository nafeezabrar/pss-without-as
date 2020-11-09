package pss.reader.config.runner;


import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.UserGroupingRunnerConfig;
import pss.config.task.PssGroupingConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.config.task.UserGenerationConfig;
import pss.config.task.UserGroupingConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.JsonPostResultProcessConfigReader;
import pss.reader.config.readers.JsonUserGroupingConfigReader;

import static pss.reader.config.readers.JsonPssGroupingConfigReader.readPssGroupingConfig;
import static pss.reader.config.readers.JsonPssTypeReader.readPssType;
import static pss.reader.config.readers.JsonPssVariableGeneratingConfigReader.readPssVariableGenerationConfig;
import static pss.reader.config.readers.JsonUserGenerationConfigReader.readUserGenerationConfig;

public class JsonUserGroupingRunnerConfigReader extends JsonConfigReader implements UserGroupingRunnerConfigReadable {

    public JsonUserGroupingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public UserGroupingRunnerConfig readUserGroupingRunnerConfig() throws InvalidConfigException, ReaderException, PssException {
        PssType pssType = readPssType(jsonFileReader);
        PssVariableGenerationConfig pssVariableGenerationConfig = readPssVariableGenerationConfig(jsonFileReader, pssType);
        PssGroupingConfig pssGroupingConfig = readPssGroupingConfig(jsonFileReader, pssType);
        UserGenerationConfig userGenerationConfig = readUserGenerationConfig(jsonFileReader);
        UserGroupingConfig userGroupingConfig = JsonUserGroupingConfigReader.readUserGroupingConfig(jsonFileReader);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new UserGroupingRunnerConfig(postResultProcessConfig, pssType, pssVariableGenerationConfig, pssGroupingConfig, userGenerationConfig, userGroupingConfig);
    }
}
