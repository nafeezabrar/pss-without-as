package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.PrintConfig;
import pss.config.runner.FullCycleRunnerConfig;
import pss.config.saving.SaveConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.Cycle.cycleRunnerConfigKey;
import static pss.reader.config.decodability.calculator.JsonDecodabilityCalculationConfigReader.readDecodabilityCalculationConfig;

public class JsonFullCycleRunnerConfigReader {
    public static FullCycleRunnerConfig readFullCycleRunnerConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException, PssException {
        JSONObject cycleRunnerJsonObject = fileReader.getJsonObject(cycleRunnerConfigKey);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(cycleRunnerJsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(cycleRunnerJsonObject, fileReader);
        DecodabilityCalculationConfig decodabilityCalculationConfig = readDecodabilityCalculationConfig(cycleRunnerJsonObject, fileReader);
        return new FullCycleRunnerConfig(saveConfig, printConfig, decodabilityCalculationConfig);
    }
}
