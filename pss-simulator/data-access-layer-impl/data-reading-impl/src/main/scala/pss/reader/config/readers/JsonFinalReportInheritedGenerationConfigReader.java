package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.task.FaultyFinalReportInheritedGenerationConfig;
import pss.config.task.FinalReportInheritedGenerationConfig;
import pss.config.task.FinalReportInheritedGenerationConfig.FinalReportInheritedGenerationMethod;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.FinalReportGeneration.finalReportGenerationConfigKey;
import static pss.config.task.FinalReportInheritedGenerationConfig.FinalReportInheritedGenerationMethod.SIMPLE_FAULTY;

public class JsonFinalReportInheritedGenerationConfigReader {
    public static FinalReportInheritedGenerationConfig readFinalReportGenerationConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        JSONObject jsonObject = fileReader.getJsonObject(finalReportGenerationConfigKey);
        String reportGenerationMethodString = fileReader.getString(jsonObject, ConfigKeyString.method);
        FinalReportInheritedGenerationMethod finalReportGenerationMethod = StringToConfig.getFinalReportInheritedGenerationMethod(reportGenerationMethodString);
        if (finalReportGenerationMethod == SIMPLE_FAULTY) {
            double percentage = fileReader.getDouble(jsonObject, ConfigKeyString.percentage);
            return new FaultyFinalReportInheritedGenerationConfig(percentage);
        }
        return new FinalReportInheritedGenerationConfig(finalReportGenerationMethod);
    }
}
