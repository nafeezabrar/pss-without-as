package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.printing.PrintConfig;
import pss.config.runner.CycleRunnerConfig;
import pss.config.saving.SaveConfig;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.Cycle.cycleRunnerConfigKey;

public class JsonCycleRunnerConfigReader {
    public static CycleRunnerConfig readCycleRunnerConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        JSONObject cycleRunnerJsonObject = fileReader.getJsonObject(cycleRunnerConfigKey);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(cycleRunnerJsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(cycleRunnerJsonObject, fileReader);
        return new CycleRunnerConfig(saveConfig, printConfig);
    }
}
