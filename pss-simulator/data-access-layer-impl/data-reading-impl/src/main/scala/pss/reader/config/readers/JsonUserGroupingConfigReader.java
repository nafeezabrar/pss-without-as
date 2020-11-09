package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.UserGroupingConfig;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

public class JsonUserGroupingConfigReader {
    public static UserGroupingConfig readUserGroupingConfig(JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        JSONObject jsonObject = jsonFileReader.getJsonObject(ConfigKeyString.UserGrouping.userGroupingConfigKey);
        String userGroupingMethodString = jsonFileReader.getString(jsonObject, ConfigKeyString.method);
        UserGroupingConfig.UserGroupingMethod userGroupingMethod = StringToConfig.getUserGroupingMethod(userGroupingMethodString);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, jsonFileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, jsonFileReader);
        return new UserGroupingConfig(userGroupingMethod, saveConfig, printConfig);
    }
}
