package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.task.FinalReportDirectGenerationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.FinalReportGeneration.finalReportGenerationConfigKey;
import static pss.config.task.FinalReportDirectGenerationConfig.FinalReportDirectGenerationMethod;

public class JsonFinalReportDirectGenerationConfigReader {
    public static FinalReportDirectGenerationConfig readFinalReportGenerationConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        JSONObject jsonObject = fileReader.getJsonObject(finalReportGenerationConfigKey);
        String reportGenerationMethodString = fileReader.getString(jsonObject, ConfigKeyString.method);
        FinalReportDirectGenerationMethod finalReportGenerationMethod = StringToConfig.getFinalReportDirectGenerationMethod(reportGenerationMethodString);
        return new FinalReportDirectGenerationConfig(finalReportGenerationMethod);
    }
}
