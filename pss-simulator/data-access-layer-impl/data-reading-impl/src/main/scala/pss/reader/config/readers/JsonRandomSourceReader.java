package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.GivenRandomSource;
import pss.config.InheritedRandomSource;
import pss.config.RandomSource;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

public class JsonRandomSourceReader {
    public static RandomSource readRandomSource(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        if (jsonFileReader.containsKey(jsonObject, ConfigKeyString.seed)) {
            long seed = jsonFileReader.getLong(jsonObject, ConfigKeyString.seed);
            return new GivenRandomSource(seed);
        } else {
            return new InheritedRandomSource();
        }
    }
}
