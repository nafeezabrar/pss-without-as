package pss.reader.config.runner;

import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.AnonymizingRunnerConfig;
import pss.config.runner.CycleRunnerConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.*;

import static pss.reader.config.readers.JsonPssTypeReader.readPssType;
import static pss.reader.config.readers.JsonPssVariableGeneratingConfigReader.readPssVariableGenerationConfig;
import static pss.reader.config.readers.JsonUserGenerationConfigReader.readUserGenerationConfig;

public class JsonAnonymizingRunnerConfigReader extends JsonConfigReader implements AnonymizingRunnerConfigReadable {
    public JsonAnonymizingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public AnonymizingRunnerConfig readAnonymizingRunnerConfig() throws InvalidConfigException, ReaderException, PssException {
        PssType pssType = readPssType(jsonFileReader);
        long seed = JsonSeedReader.readSeed(jsonFileReader);
        PssVariableGenerationConfig pssVariableGenerationConfig = readPssVariableGenerationConfig(jsonFileReader, pssType);
        PssGroupingConfig pssGroupingConfig = JsonPssGroupingConfigReader.readPssGroupingConfig(jsonFileReader, pssType);
        UserGenerationConfig userGenerationConfig = readUserGenerationConfig(jsonFileReader);
        UserGroupingConfig userGroupingConfig = JsonUserGroupingConfigReader.readUserGroupingConfig(jsonFileReader);
        ObservedReportGenerationConfig observedReportGenerationConfig = JsonObservedReportGenerationConfigReader.readObservedReportGenerationConfig(jsonFileReader);
        AnonymizationConfig anonymizationConfig = JsonAnonymizationConfigReader.readPssGroupingConfig(jsonFileReader);
        AnonymityGenerationConfig anonymityGenerationConfig = JsonAnonymityGenerationConfigReader.readAnonymityGenerationConfig(pssType, jsonFileReader);
        CycleRunnerConfig cycleRunnerConfig = JsonCycleRunnerConfigReader.readCycleRunnerConfig(jsonFileReader);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);
        return new AnonymizingRunnerConfig(postResultProcessConfig, pssType, pssVariableGenerationConfig, pssGroupingConfig, userGenerationConfig, userGroupingConfig, observedReportGenerationConfig, anonymityGenerationConfig, anonymizationConfig, cycleRunnerConfig);
    }
}
