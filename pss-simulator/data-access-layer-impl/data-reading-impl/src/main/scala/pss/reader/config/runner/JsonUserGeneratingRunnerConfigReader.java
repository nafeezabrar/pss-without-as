package pss.reader.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.UserGeneratingRunnerConfig;
import pss.config.task.UserGenerationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.JsonPostResultProcessConfigReader;
import pss.reader.config.readers.JsonUserGenerationConfigReader;

public class JsonUserGeneratingRunnerConfigReader extends JsonConfigReader implements UserGeneratingRunnerConfigReadable {
    public JsonUserGeneratingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public UserGeneratingRunnerConfig readUserGeneratingRunnerConfig() throws InvalidConfigException, ReaderException, PssException {
        UserGenerationConfig userGenerationConfig = JsonUserGenerationConfigReader.readUserGenerationConfig(jsonFileReader);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new UserGeneratingRunnerConfig(postResultProcessConfig, userGenerationConfig);
    }
}
