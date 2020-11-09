package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.data.anonymity.SingleRasAnonymity;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.AnonymityGeneration.anonymityKey;
import static pss.config.ConfigKeyString.AnonymityGeneration.replacingAnonymityKey;

public class SingleJsonRasAnonymityReader extends JsonAnonymityReader<SingleRasAnonymity> {


    public SingleJsonRasAnonymityReader(JSONObject jsonObject, JsonFileReader jsonFileReader) {
        super(jsonObject, jsonFileReader);
    }

    @Override
    public SingleRasAnonymity readAnonymityConfig(String key) throws ReaderException, InvalidConfigException {
        int totalAnonymity = jsonFileReader.getInt(jsonObject, anonymityKey);
        int replacingAnonymity = jsonFileReader.getInt(jsonObject, replacingAnonymityKey);
        return new SingleRasAnonymity(totalAnonymity, replacingAnonymity);
    }

}
