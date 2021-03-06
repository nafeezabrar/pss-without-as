package pss.reader.config.runner;

import pss.config.adversary.AdversaryConfig;
import pss.config.post.result.process.PostResultProcessConfig;
import pss.config.runner.FullCycleRunnerConfig;
import pss.config.runner.FullSimulationWithAdversaryTillDecodingRunnerConfig;
import pss.config.task.*;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.readers.*;

import static pss.reader.config.readers.JsonAnonymityGenerationConfigReader.readAnonymityGenerationConfig;
import static pss.reader.config.readers.JsonFullCycleRunnerConfigReader.readFullCycleRunnerConfig;
import static pss.reader.config.readers.JsonObservedReportGenerationConfigReader.readObservedReportGenerationConfig;
import static pss.reader.config.readers.JsonPssGroupingConfigReader.readPssGroupingConfig;
import static pss.reader.config.readers.JsonPssTypeReader.readPssType;
import static pss.reader.config.readers.JsonPssVariableGeneratingConfigReader.readPssVariableGenerationConfig;
import static pss.reader.config.readers.JsonUserGenerationConfigReader.readUserGenerationConfig;

public class JsonFullSimulationWithAdversaryTillDecodingRunnerConfigReader extends JsonConfigReader implements FullSimulationWithAdversaryTillDecodingRunnerConfigReadable {
    public JsonFullSimulationWithAdversaryTillDecodingRunnerConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public FullSimulationWithAdversaryTillDecodingRunnerConfig generateFullSimulationWithAdversaryTillDecodingRunnerConfig() throws InvalidConfigException, ReaderException, PssException {
        PssType pssType = readPssType(jsonFileReader);
        long seed = JsonSeedReader.readSeed(jsonFileReader);
        PssVariableGenerationConfig pssVariableGenerationConfig = readPssVariableGenerationConfig(jsonFileReader, pssType);
        PssGroupingConfig pssGroupingConfig = readPssGroupingConfig(jsonFileReader, pssType);
        UserGenerationConfig userGenerationConfig = readUserGenerationConfig(jsonFileReader);
        UserGroupingConfig userGroupingConfig = JsonUserGroupingConfigReader.readUserGroupingConfig(jsonFileReader);
        ObservedReportGenerationConfig observedReportGenerationConfig = readObservedReportGenerationConfig(jsonFileReader);
        AnonymityGenerationConfig anonymityGenerationConfig = readAnonymityGenerationConfig(pssType, jsonFileReader);
        AnonymizationConfig anonymizationConfig = JsonAnonymizationConfigReader.readPssGroupingConfig(jsonFileReader);
        DeanonymizationConfig deanonymizationConfig = JsonDeanonymizationConfigReader.readDeanonymimzationConfig(jsonFileReader);
        FullCycleRunnerConfig cycleRunnerConfig = readFullCycleRunnerConfig(jsonFileReader);
        FinalReportInheritedGenerationConfig finalReportGenerationConfig = JsonFinalReportInheritedGenerationConfigReader.readFinalReportGenerationConfig(jsonFileReader);
        AdversaryConfig adversaryConfig = JsonAdversaryConfigReader.readAdversaryConfig(jsonFileReader);
        JsonPostResultProcessConfigReader postResultProcessConfigReader = new JsonPostResultProcessConfigReader(jsonFileReader);
        PostResultProcessConfig postResultProcessConfig = postResultProcessConfigReader.readPostResultProcessConfig(jsonFileReader);

        return new FullSimulationWithAdversaryTillDecodingRunnerConfig(postResultProcessConfig, pssType, pssVariableGenerationConfig, pssGroupingConfig, userGenerationConfig, userGroupingConfig, observedReportGenerationConfig, anonymityGenerationConfig, anonymizationConfig, deanonymizationConfig, finalReportGenerationConfig, cycleRunnerConfig, adversaryConfig);
    }

}
