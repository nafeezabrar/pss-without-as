package pss.reader.config.runner;

import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.ResultFilteringRunnerConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.JsonPostResultProcessConfigReader;

import static pss.config.ConfigKeyString.fileNameKey;
import static pss.config.runner.ResultFilteringRunnerConfig.ResultFilteringType;

public class JsonResultFilteringRunnerConfigReader extends JsonConfigReader implements ResultFilteringRunnerConfigReadable {
    public JsonResultFilteringRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public ResultFilteringRunnerConfig readResultFilteringRunnerConfig() throws ReaderException, InvalidConfigException, PssException {
        String typeKey = jsonFileReader.getString(ConfigKeyString.ResultFiltering.typeKey);
        ResultFilteringType resultFilteringType = StringToConfig.getResultFilteringType(typeKey);
        String inputFileName = jsonFileReader.getString(fileNameKey);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new ResultFilteringRunnerConfig(postResultProcessConfig, resultFilteringType, inputFileName);
    }
}
