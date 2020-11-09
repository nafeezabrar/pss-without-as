package pss.reader.config.runner;


import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.PssGroupingRunnerConfig;
import pss.config.task.PssGroupingConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.JsonPostResultProcessConfigReader;
import pss.reader.config.readers.JsonPssGroupingConfigReader;

import static pss.reader.config.readers.JsonPssTypeReader.readPssType;
import static pss.reader.config.readers.JsonPssVariableGeneratingConfigReader.readPssVariableGenerationConfig;

public class JsonPssGroupingRunnerConfigReader extends JsonConfigReader implements PssGroupingRunnerConfigReadable {

    public JsonPssGroupingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public PssGroupingRunnerConfig generatePssGroupingRunnerConfig() throws InvalidConfigException, ReaderException, PssException {
        PssType pssType = readPssType(jsonFileReader);
        PssVariableGenerationConfig pssVariableGenerationConfig = readPssVariableGenerationConfig(jsonFileReader, pssType);
        PssGroupingConfig pssGroupingConfig = JsonPssGroupingConfigReader.readPssGroupingConfig(jsonFileReader, pssType);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new PssGroupingRunnerConfig(postResultProcessConfig, pssType, pssVariableGenerationConfig, pssGroupingConfig);
    }
}
