package pss.reader.config.readers;

import com.pss.adversary.Adversary.AdversaryType;
import org.json.simple.JSONObject;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.Adversary.adversaryType;
import static pss.config.StringToConfig.getAdversaryType;

public class JsonAdversaryTypeReader {
    public static AdversaryType readAdversaryTypeConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        String adversaryTypeString = jsonFileReader.getString(jsonObject, adversaryType);
        return getAdversaryType(adversaryTypeString);
    }
}
