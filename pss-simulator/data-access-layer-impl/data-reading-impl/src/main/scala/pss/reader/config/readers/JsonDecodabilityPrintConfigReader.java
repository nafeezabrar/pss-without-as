package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.printing.DecodabilityPrintConfig;
import pss.config.printing.DecodabilityPrintConfig.PrintingSource;
import pss.config.printing.InheritedDecodabilityPrintConfig;
import pss.config.printing.SeperateDecodabilityPrintConfig;
import pss.config.printing.ShouldPrintConfig;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.DecodabilityCalculation.decodabilityPrintConfigKey;
import static pss.config.printing.DecodabilityPrintConfig.PrintingSource.SEPARATE;

public class JsonDecodabilityPrintConfigReader {
    public static DecodabilityPrintConfig readPrintConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        if (jsonFileReader.containsKey(jsonObject, ConfigKeyString.print)) {
            String decodabilityPrintSourceString = jsonFileReader.getString(jsonObject, decodabilityPrintConfigKey);
            PrintingSource printingSource = StringToConfig.getDecodabilityPrintingSource(decodabilityPrintSourceString);
            switch (printingSource) {
                case INHERITED:
                    return readInheritedPrintConfig();
                case SEPARATE:
                    return readSeparatePrintConfig(jsonFileReader, jsonObject);
            }
            throw new InvalidConfigException(String.format("Decodability printing source %s not found", printingSource.toString()));
        }
        throw new InvalidConfigException("no printing config found");
    }

    private static DecodabilityPrintConfig readSeparatePrintConfig(JsonFileReader jsonFileReader, JSONObject jsonObject) throws InvalidConfigException, ReaderException {
        ShouldPrintConfig printConfig = (ShouldPrintConfig) JsonPrintConfigReader.readPrintConfig(jsonObject, jsonFileReader);
        return new SeperateDecodabilityPrintConfig(SEPARATE, printConfig);
    }

    private static DecodabilityPrintConfig readInheritedPrintConfig() {
        return new InheritedDecodabilityPrintConfig();
    }
}
