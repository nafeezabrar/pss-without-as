package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.data.anonymity.MultiAasAnonymity;
import pss.data.dimension.Dimension;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static pss.config.ConfigKeyString.AnonymityGeneration.addingAnonymityKey;
import static pss.config.ConfigKeyString.AnonymityGeneration.anonymityKey;

public class MultiJsonAasAnonymityReader extends JsonAnonymityReader<MultiAasAnonymity> {
    protected final Set<Dimension> dimensionSet;

    public MultiJsonAasAnonymityReader(JSONObject jsonObject, JsonFileReader jsonFileReader, Set<Dimension> dimensionSet) {
        super(jsonObject, jsonFileReader);
        this.dimensionSet = dimensionSet;
    }

    public MultiAasAnonymity readAnonymityConfig(String key) throws ReaderException, InvalidConfigException {
        Map<Dimension, Integer> anonymityMap = new HashMap<>();
        Map<Dimension, Integer> addingAnonymityMap = new HashMap<>();
        Set<JSONObject> anonymityJsonObjects = jsonFileReader.getJsonObjects(this.jsonObject, anonymityKey);
        if (anonymityJsonObjects.size() < dimensionSet.size()) {
            throw new InvalidConfigException("Anonymity not given for all dimensions");
        }
        for (JSONObject anonymityJsonObject : anonymityJsonObjects) {
            Map<Dimension, Integer> anonymityPair = readAnonymity(anonymityJsonObject);
            Map<Dimension, Integer> addingAnonymityPair = readAddingAnonymity(anonymityJsonObject);
            anonymityMap.putAll(anonymityPair);
            addingAnonymityMap.putAll(addingAnonymityPair);
        }
        return new MultiAasAnonymity(anonymityMap, addingAnonymityMap);
    }

    private Map<Dimension, Integer> readAddingAnonymity(JSONObject anonymityJsonObject) throws ReaderException, InvalidConfigException {
        String dimensionKey = jsonFileReader.getString(anonymityJsonObject, ConfigKeyString.dimensionKey);
        Dimension dimension = getDimensionFromString(dimensionKey);
        HashMap<Dimension, Integer> anonymityPair = new HashMap<>();
        int anonymity = jsonFileReader.getInt(anonymityJsonObject, addingAnonymityKey);
        anonymityPair.put(dimension, anonymity);
        return anonymityPair;
    }

    private Map<Dimension, Integer> readAnonymity(JSONObject anonymityJsonObject) throws ReaderException, InvalidConfigException {
        String dimensionKey = jsonFileReader.getString(anonymityJsonObject, ConfigKeyString.dimensionKey);
        Dimension dimension = getDimensionFromString(dimensionKey);
        HashMap<Dimension, Integer> anonymityPair = new HashMap<>();
        int anonymity = jsonFileReader.getInt(anonymityJsonObject, anonymityKey);
        anonymityPair.put(dimension, anonymity);
        return anonymityPair;
    }

    private Dimension getDimensionFromString(String dimensionKey) throws InvalidConfigException {
        for (Dimension dimension : dimensionSet) {
            if (dimension.getName().equals(dimensionKey))
                return dimension;
        }
        throw new InvalidConfigException(String.format("Anonymity set for unrecognized dimension %s", dimensionKey));
    }

}
