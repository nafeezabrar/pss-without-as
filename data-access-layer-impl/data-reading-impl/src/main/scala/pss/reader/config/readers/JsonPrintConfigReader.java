package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.PrintingTargetType;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.printing.NoPrintConfig;
import pss.config.printing.PrintConfig;
import pss.config.printing.ShouldPrintConfigWithFileName;
import pss.config.printing.ShouldPrintConfigWithoutFileName;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.printFileNameKey;
import static pss.config.printing.ShouldPrintConfig.AppendMode;

public class JsonPrintConfigReader {
    public static PrintConfig readPrintConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        if (jsonFileReader.containsKey(jsonObject, ConfigKeyString.print)) {
            String printTargetTypeString = jsonFileReader.getString(jsonObject, ConfigKeyString.printTargetType);
            PrintingTargetType printingTargetType = StringToConfig.getPrintingTargetType(printTargetTypeString);
            AppendMode appendMode = JsonAppendModeReader.readAppendMode(jsonObject, jsonFileReader);
            if (jsonFileReader.containsKey(jsonObject, printFileNameKey)) {
                String fileName = jsonFileReader.getString(jsonObject, printFileNameKey);
                return new ShouldPrintConfigWithFileName(printingTargetType, appendMode, fileName);
            } else {
                return new ShouldPrintConfigWithoutFileName(printingTargetType, appendMode);
            }

        } else {
            return new NoPrintConfig();
        }
    }
}
