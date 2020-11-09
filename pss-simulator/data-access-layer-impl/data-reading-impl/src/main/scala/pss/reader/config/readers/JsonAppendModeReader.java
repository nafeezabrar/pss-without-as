package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.printing.ShouldPrintConfig.AppendMode;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.appendKey;

public class JsonAppendModeReader {
    public static AppendMode readAppendMode(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException {
        boolean containsKey = fileReader.containsKey(jsonObject, appendKey);
        if (containsKey) {
            String appendModeString = fileReader.getString(jsonObject, appendKey);
            if (appendModeString.equals(ConfigKeyString.yes)) {
                return AppendMode.SHOULD_APPEND;
            }
        }
        return AppendMode.NOT_APPEND;
    }
}
