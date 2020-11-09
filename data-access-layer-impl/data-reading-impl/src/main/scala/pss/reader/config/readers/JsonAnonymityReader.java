package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.data.anonymity.Anonymity;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

public abstract class JsonAnonymityReader<TAnonymity extends Anonymity> {
    protected final JSONObject jsonObject;
    protected final JsonFileReader jsonFileReader;

    protected JsonAnonymityReader(JSONObject jsonObject, JsonFileReader jsonFileReader) {
        this.jsonObject = jsonObject;
        this.jsonFileReader = jsonFileReader;
    }

    abstract public TAnonymity readAnonymityConfig(String key) throws ReaderException, InvalidConfigException;
}
