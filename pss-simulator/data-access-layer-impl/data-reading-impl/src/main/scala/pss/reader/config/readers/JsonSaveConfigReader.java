package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.SavingTargetType;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.saving.NoSaveConfig;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

public class JsonSaveConfigReader {
    public static SaveConfig readSaveConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        if (jsonFileReader.containsKey(jsonObject, ConfigKeyString.save)) {
            String fileName = jsonFileReader.getString(jsonObject, ConfigKeyString.saveFileNameKey);
            String saveTargetTypeString = jsonFileReader.getString(jsonObject, ConfigKeyString.saveTargetType);
            SavingTargetType savingTargetType = StringToConfig.getSavingTargetType(saveTargetTypeString);
            return new ShouldSaveConfig(savingTargetType, fileName);
        } else {
            return new NoSaveConfig();
        }
    }
}
