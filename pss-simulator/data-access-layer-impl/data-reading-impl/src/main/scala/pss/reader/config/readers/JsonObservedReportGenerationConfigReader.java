package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.ReadingSourceType;
import pss.config.ConfigKeyString;
import pss.config.RandomSource;
import pss.config.StringToConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.FromFileObservedReportGenerationConfig;
import pss.config.task.ObservedReportGenerationConfig;
import pss.config.task.ObservedReportGenerationConfig.ReportGenerationMethod;
import pss.config.task.RandomObservedReportSetGenerationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.ObservedReportGeneration.observedReportGenerationConfigKey;
import static pss.config.ConfigKeyString.fileNameKey;
import static pss.config.ConfigKeyString.method;

public class JsonObservedReportGenerationConfigReader {
    public static ObservedReportGenerationConfig readObservedReportGenerationConfig(JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException, PssException {
        JSONObject jsonObject = jsonFileReader.getJsonObject(observedReportGenerationConfigKey);
        String reportGenerationMethodString = jsonFileReader.getString(jsonObject, method);
        ReportGenerationMethod reportGenerationMethod = StringToConfig.getReportGenerationMethod(reportGenerationMethodString);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, jsonFileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, jsonFileReader);
        switch (reportGenerationMethod) {

            case FROM_FILE:
                return generateFromFileObservedReportConfig(jsonFileReader, jsonObject, saveConfig, printConfig);
            case RANDOM:
                return generateRandomObservedReportConfig(jsonFileReader, jsonObject, saveConfig, printConfig);
        }
        throw new PssException(String.format("Report generation config method %s not matched", reportGenerationMethod.toString()));
    }

    private static ObservedReportGenerationConfig generateRandomObservedReportConfig(JsonFileReader jsonFileReader, JSONObject jsonObject, SaveConfig saveConfig, PrintConfig printConfig) throws ReaderException, InvalidConfigException {
        RandomSource randomSource = JsonRandomSourceReader.readRandomSource(jsonObject, jsonFileReader);
        int reportCount = jsonFileReader.getInt(jsonObject, ConfigKeyString.ObservedReportGeneration.reportCountKey);
        return new RandomObservedReportSetGenerationConfig(saveConfig, printConfig, randomSource, reportCount);
    }

    private static ObservedReportGenerationConfig generateFromFileObservedReportConfig(JsonFileReader jsonFileReader, JSONObject jsonObject, SaveConfig saveConfig, PrintConfig printConfig) throws ReaderException, InvalidConfigException {
        String fileName = jsonFileReader.getString(jsonObject, fileNameKey);
        String readingSourceTypeString = jsonFileReader.getString(jsonObject, ConfigKeyString.readingSourceKey);
        ReadingSourceType readingSourceType = StringToConfig.getSourceType(readingSourceTypeString);
        return new FromFileObservedReportGenerationConfig(ReportGenerationMethod.FROM_FILE, saveConfig, printConfig, fileName, readingSourceType);
    }
}
