package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.task.DeanonymizationConfig;
import pss.config.task.DeanonymizationConfig.DeanonymizationMethod;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.Deanonymization.deanonymizationConfigKey;

public class JsonDeanonymizationConfigReader {
    public static DeanonymizationConfig readDeanonymimzationConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        JSONObject jsonObject = fileReader.getJsonObject(deanonymizationConfigKey);
        return readDeanonymimzationConfig(jsonObject, fileReader);
    }

    public static DeanonymizationConfig readDeanonymimzationConfig(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        String deanonymizaitonMethodString = fileReader.getString(jsonObject, ConfigKeyString.method);
        DeanonymizationMethod deanonymizationMethod = StringToConfig.getDeanonymizationMethod(deanonymizaitonMethodString);
        return new DeanonymizationConfig(deanonymizationMethod);
    }
}
