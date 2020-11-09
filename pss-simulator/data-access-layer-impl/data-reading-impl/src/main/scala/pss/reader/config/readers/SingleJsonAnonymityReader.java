package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.data.anonymity.SingleAnonymity;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.AnonymityGeneration.anonymityKey;

public class SingleJsonAnonymityReader extends JsonAnonymityReader<SingleAnonymity> {


    public SingleJsonAnonymityReader(JSONObject jsonObject, JsonFileReader jsonFileReader) {
        super(jsonObject, jsonFileReader);
    }

    @Override
    public SingleAnonymity readAnonymityConfig(String key) throws ReaderException {
        int anonymity = jsonFileReader.getInt(jsonObject, anonymityKey);
        return new SingleAnonymity(anonymity);
    }
}
