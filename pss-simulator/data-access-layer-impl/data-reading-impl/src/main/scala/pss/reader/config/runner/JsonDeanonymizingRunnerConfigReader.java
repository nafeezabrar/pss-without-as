package pss.reader.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.DeanonymizingRunnerConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.*;

import static pss.reader.config.readers.JsonFinalReportDirectGenerationConfigReader.readFinalReportGenerationConfig;
import static pss.reader.config.readers.JsonPssGroupingConfigReader.readPssGroupingConfig;
import static pss.reader.config.readers.JsonPssTypeReader.readPssType;
import static pss.reader.config.readers.JsonPssVariableGeneratingConfigReader.readPssVariableGenerationConfig;

public class JsonDeanonymizingRunnerConfigReader extends JsonConfigReader implements DeanonymizingRunnerConfigReadable {
    public JsonDeanonymizingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public DeanonymizingRunnerConfig readDeanonymizingRunnerConfig() throws InvalidConfigException, ReaderException, PssException {
        PssType pssType = readPssType(jsonFileReader);
        long seed = JsonSeedReader.readSeed(jsonFileReader);
        PssVariableGenerationConfig pssVariableGenerationConfig = readPssVariableGenerationConfig(jsonFileReader, pssType);
        PssGroupingConfig pssGroupingConfig = readPssGroupingConfig(jsonFileReader, pssType);
        FinalReportDirectGenerationConfig finalReportGenerationConfig = readFinalReportGenerationConfig(jsonFileReader);
        DeanonymizationConfig deanonymizationConfig = JsonDeanonymizationConfigReader.readDeanonymimzationConfig(jsonFileReader);
        UserGenerationConfig userGenerationConfig = JsonUserGenerationConfigReader.readUserGenerationConfig(jsonFileReader);
        UserGroupingConfig userGroupingConfig = JsonUserGroupingConfigReader.readUserGroupingConfig(jsonFileReader);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new DeanonymizingRunnerConfig(postResultProcessConfig, pssType, pssVariableGenerationConfig, pssGroupingConfig, userGenerationConfig, userGroupingConfig, finalReportGenerationConfig, deanonymizationConfig);
    }
}
