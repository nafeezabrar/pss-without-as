package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.RandomSource;
import pss.config.StringToConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.UserGenerationConfig;
import pss.config.task.UserGenerationConfig.UserGenerationMethod;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.UserGeneration.totalUsersKey;

public class JsonUserGenerationConfigReader {
    public static UserGenerationConfig readUserGenerationConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        JSONObject jsonObject = fileReader.getJsonObject(ConfigKeyString.UserGeneration.userGenerationConfigKey);
        String userGenerationMethodString = fileReader.getString(jsonObject, ConfigKeyString.method);
        UserGenerationMethod userGenerationMethod = StringToConfig.getUserGenerationMethod(userGenerationMethodString);
        int totalUsers = fileReader.getInt(jsonObject, totalUsersKey);
        if (totalUsers <= 0) {
            throw new InvalidConfigException("Total Users is Not Positive");
        }
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, fileReader);
        RandomSource randomSource = JsonRandomSourceReader.readRandomSource(jsonObject, fileReader);
        return new UserGenerationConfig(totalUsers, userGenerationMethod, saveConfig, printConfig, randomSource);
    }
}
