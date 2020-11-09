package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.data.anonymity.Anonymity;
import pss.data.anonymity.SingleAnonymity;
import pss.data.anonymity.UniformAnonymity;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import java.util.ArrayList;
import java.util.List;

import static pss.config.ConfigKeyString.AnonymityGeneration.anonymityKey;

public class SingleJsonUniformAnonymityReader extends JsonAnonymityReader<UniformAnonymity> {


    public SingleJsonUniformAnonymityReader(JSONObject jsonObject, JsonFileReader jsonFileReader) {
        super(jsonObject, jsonFileReader);
    }

    @Override
    public UniformAnonymity readAnonymityConfig(String key) throws ReaderException {
        int[] anonymities = jsonFileReader.getIntArray(jsonObject, anonymityKey);
        List<Anonymity> anonymityList = new ArrayList<>();
        for (int i = 0; i < anonymities.length; i++) {
            anonymityList.add(new SingleAnonymity(anonymities[i]));
        }
        return new UniformAnonymity(anonymityList);
    }
}
