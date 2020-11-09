package pss.reader.config.runner;


import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.PssVariableGenerationRunnerConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.JsonPostResultProcessConfigReader;

import static pss.reader.config.readers.JsonPssTypeReader.readPssType;
import static pss.reader.config.readers.JsonPssVariableGeneratingConfigReader.readPssVariableGenerationConfig;

public class JsonPssVariableGeneratingRunnerConfigReader extends JsonConfigReader implements PssVariableGeneratingRunnerConfigReadable {

    public JsonPssVariableGeneratingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public PssVariableGenerationRunnerConfig readPssVariableGeneratingRunnerConfig() throws ReaderException, InvalidConfigException, PssException {
        PssType pssType = readPssType(jsonFileReader);
        PssVariableGenerationConfig pssVariableGenerationConfig = readPssVariableGenerationConfig(jsonFileReader, pssType);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new PssVariableGenerationRunnerConfig(postResultProcessConfig, pssType, pssVariableGenerationConfig);
    }
}
