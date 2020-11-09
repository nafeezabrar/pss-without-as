package pss.reader.config.readers;

import pss.config.ConfigKeyString;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

public class JsonSeedReader {
    public static long readSeed(JsonFileReader jsonFileReader) throws ReaderException {
        if (jsonFileReader.containsKey(ConfigKeyString.seed)) {
            return jsonFileReader.getLong(ConfigKeyString.seed);
        }
        return 0;
    }
}
