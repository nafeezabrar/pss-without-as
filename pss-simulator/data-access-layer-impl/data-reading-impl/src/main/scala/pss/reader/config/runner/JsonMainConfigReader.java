package pss.reader.config.runner;

import pss.ReadingSourceType;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.runner.RunnerConfig;
import pss.config.runner.RunnerType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.JsonConfigReader;
import pss.reader.config.factory.runner.*;

import java.util.HashMap;
import java.util.Map;

import static pss.reader.config.factory.runner.FullSimulationWithAdversaryRunnerConfigReadableFactory.generateRunnerConfigReader;

public class JsonMainConfigReader extends JsonConfigReader implements RunnerConfigReadable {

    public JsonMainConfigReader(String fileName) throws ReaderException {
        super(fileName);
    }

    @Override
    public RunnerConfig readPssRunnerConfig() throws ReaderException, InvalidConfigException, PssException {
        String runnerTypeString = jsonFileReader.getString(ConfigKeyString.Runner.runnerType);
        RunnerType runnerType = StringToConfig.getRunnerType(runnerTypeString);
        String sourceTypeString = jsonFileReader.getString(ConfigKeyString.configSourceType);
        ReadingSourceType readingSourceType = StringToConfig.getSourceType(sourceTypeString);
        String fileName = jsonFileReader.getString(ConfigKeyString.fileNameKey);
        return generatePssRunnerConfig(runnerType, readingSourceType, fileName);
    }

    @Override
    public Map<String, RunnerConfig> readPssRunnerConfigs() throws ReaderException, InvalidConfigException, PssException {
        String runnerTypeString = jsonFileReader.getString(ConfigKeyString.Runner.runnerType);
        RunnerType runnerType = StringToConfig.getRunnerType(runnerTypeString);
        String sourceTypeString = jsonFileReader.getString(ConfigKeyString.configSourceType);
        ReadingSourceType readingSourceType = StringToConfig.getSourceType(sourceTypeString);
        Map<String, RunnerConfig> runnerConfigMap = new HashMap<>();
        String[] fileNames = jsonFileReader.getStringArray(ConfigKeyString.fileNamesKey);
        for (String fileName : fileNames) {
            RunnerConfig runnerConfig = generatePssRunnerConfig(runnerType, readingSourceType, fileName);
            runnerConfigMap.put(fileName, runnerConfig);
        }
        return runnerConfigMap;
    }

    private RunnerConfig generatePssRunnerConfig(RunnerType runnerTypeInput, ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        switch (runnerTypeInput) {
            case PSS_VARIABLE_GENERATING_RUNNER:
                return readPssVariableGeneratingRunnerConfig(readingSourceType, fileName);
            case USER_GROUPING_RUNNER:
                return readUserGroupingRunnerConfig(readingSourceType, fileName);
            case REPORT_GENERATING_RUNNER:
                return readReportGeneratingRunnerConfig(readingSourceType, fileName);
            case ANONYMIZING_RUNNER:
                return readAnonymizingRunnerConfig(readingSourceType, fileName);
            case DEANONYMIZING_RUNNER:
                return readDeanonymizingRunnerConfig(readingSourceType, fileName);
            case FULL_SIMULATION_RUNNER:
                return readFullSimulationRunnerConfig(readingSourceType, fileName);
            case RESULT_FILTERING_RUNNER:
                return readResultFilteringRunnerConfig(readingSourceType, fileName);
            case PSS_GROUPING_RUNNER:
                return readPssGroupingRunnerConfig(readingSourceType, fileName);
            case USER_GENERATING_RUNNER:
                return readUserGeneratingRunnerConfig(readingSourceType, fileName);
            case FULL_SIMULATION_WITH_ADVERSARY:
                return readFullSimulationWithAdversaryRunnerConfig(readingSourceType, fileName);
            case FULL_SIMULATION_WITH_ADVERSARY_TILL_DECODING:
                return readFullSimulationWithAdversaryTillDecodingRunnerConfig(readingSourceType, fileName);
            case FULL_SIMULATION_WITH_GENERATED_ADVERSARY:
                return readFullSimulationWithGeneratedAdversaryRunnerConfig(readingSourceType, fileName);
            case FULL_SIMULATION_WITH_GENERATED_ADVERSARY_TILL_DECODING:
                return readFullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig(readingSourceType, fileName);
            case FULL_SIMULATION_TILL_DECODING:
                return readFullSimulationTillDecodingRunnerConfig(readingSourceType, fileName);
        }
        throw new InvalidConfigException(String.format("Invalid Pss Runner typeKey %s", runnerTypeInput));
    }

    private RunnerConfig readUserGroupingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException, PssException {
        UserGroupingRunnerConfigReadable configReader = UserGroupingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.readUserGroupingRunnerConfig();
    }

    private RunnerConfig readDeanonymizingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        DeanonymizingRunnerConfigReadable configReader = DeanonymizingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.readDeanonymizingRunnerConfig();
    }

    private RunnerConfig readAnonymizingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        AnonymizingRunnerConfigReadable configReader = AnonymizingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.readAnonymizingRunnerConfig();
    }

    private RunnerConfig readFullSimulationRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        FullSimulationRunnerConfigReadable configReader = FullSimulationRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.generateFullSimulationRunnerConfig();

    }

    private RunnerConfig readResultFilteringRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        ResultFilteringRunnerConfigReadable configReader = ResultFilteringRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.readResultFilteringRunnerConfig();
    }

    private RunnerConfig readPssVariableGeneratingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException, PssException {
        PssVariableGeneratingRunnerConfigReadable configReader = PssVariableGeneratingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.readPssVariableGeneratingRunnerConfig();
    }

    private RunnerConfig readReportGeneratingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        ObservedReportGeneratingRunnerConfigReadable configReader = ObservedReportGeneratingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.generateObservedReportGeneratingRunnerConfig();
    }

    private RunnerConfig readPssGroupingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException, PssException {
        PssGroupingRunnerConfigReadable configReader = PssGroupingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.generatePssGroupingRunnerConfig();
    }

    private RunnerConfig readUserGeneratingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws ReaderException, InvalidConfigException, PssException {
        UserGeneratingRunnerConfigReadable configReader = UserGeneratingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.readUserGeneratingRunnerConfig();
    }

    private RunnerConfig readFullSimulationWithAdversaryRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        FullSimulationWithAdversaryRunnerConfigReadable fullSimulationWithAdversaryRunnerConfigReader = generateRunnerConfigReader(readingSourceType, fileName);
        return fullSimulationWithAdversaryRunnerConfigReader.generateFullSimulationWithAdversaryRunnerConfig();
    }

    private RunnerConfig readFullSimulationWithAdversaryTillDecodingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        FullSimulationWithAdversaryTillDecodingRunnerConfigReadable configReader = FullSimulationWithAdversaryTillDecodingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return configReader.generateFullSimulationWithAdversaryTillDecodingRunnerConfig();
    }

    private RunnerConfig readFullSimulationWithGeneratedAdversaryRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        FullSimulationWithGeneratedAdversaryRunnerConfigReadable fullSimulationWithGeneratedAdversaryRunnerConfigReader = FullSimulationWithGeneratedAdversaryRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return fullSimulationWithGeneratedAdversaryRunnerConfigReader.generateFullSimulationWithGeneratedAdversaryRunnerConfig();
    }

    private RunnerConfig readFullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfigReadable fullSimulationWithGeneratedAdversaryRunnerConfigReader = FullSimulationWithGeneratedAdversaryTillDecodingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return fullSimulationWithGeneratedAdversaryRunnerConfigReader.generateFullSimulationWithGeneratedAdversaryTillDecodingRunnerConfig();
    }

    private RunnerConfig readFullSimulationTillDecodingRunnerConfig(ReadingSourceType readingSourceType, String fileName) throws InvalidConfigException, ReaderException, PssException {
        FullSimulationTillDecodingRunnerConfigReadable runnerConfigReader = FullSimulationTillDecodingRunnerConfigReadableFactory.generateRunnerConfigReader(readingSourceType, fileName);
        return runnerConfigReader.generateFullSimulationTillDecodingRunnerConfig();
    }
}
