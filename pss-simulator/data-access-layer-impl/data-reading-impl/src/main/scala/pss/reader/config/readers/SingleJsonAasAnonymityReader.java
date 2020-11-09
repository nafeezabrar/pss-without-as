package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.data.anonymity.SingleAasAnonymity;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.AnonymityGeneration.addingAnonymityKey;
import static pss.config.ConfigKeyString.AnonymityGeneration.anonymityKey;

public class SingleJsonAasAnonymityReader extends JsonAnonymityReader<SingleAasAnonymity> {


    public SingleJsonAasAnonymityReader(JSONObject jsonObject, JsonFileReader jsonFileReader) {
        super(jsonObject, jsonFileReader);
    }

    @Override
    public SingleAasAnonymity readAnonymityConfig(String key) throws ReaderException, InvalidConfigException {
        int totalAnonymity = jsonFileReader.getInt(jsonObject, anonymityKey);
        int addingAnonymity = jsonFileReader.getInt(jsonObject, addingAnonymityKey);
        return new SingleAasAnonymity(totalAnonymity, addingAnonymity);
    }

}
