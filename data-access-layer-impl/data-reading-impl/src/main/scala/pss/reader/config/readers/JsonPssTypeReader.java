package pss.reader.config.readers;

import javafx.util.Pair;
import org.json.simple.JSONObject;
import pss.config.StringToConfig;
import pss.data.dimension.Dimension;
import pss.data.pss_type.PssType;
import pss.data.pss_type.PssType.PssDimensionType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static pss.config.ConfigKeyString.PssType.*;
import static pss.config.ConfigKeyString.*;
import static pss.data.pss_type.PssType.PssDimensionType.MULTI;
import static pss.data.pss_type.PssType.PssDimensionType.SINGLE;

public class JsonPssTypeReader {
    public static PssType readPssType(JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        JSONObject pssTypeJsonObject = fileReader.getJsonObject(pssType);
        String dimensionTypeString = fileReader.getString(pssTypeJsonObject, dimensionType);
        PssDimensionType dimensionType = StringToConfig.getDimensionType(dimensionTypeString);

        if (dimensionType == SINGLE) {
            return readSinglePssType(fileReader, pssTypeJsonObject);
        } else {
            return readMultiPssType(fileReader, pssTypeJsonObject);
        }
    }


    private static PssType readSinglePssType(JsonFileReader fileReader, JSONObject pssTypeJsonObject) throws ReaderException {
        JSONObject dimensionJsonObject = fileReader.getJsonObject(pssTypeJsonObject, dimensionKey);
        Map<Dimension, Integer> nSet = new HashMap<>();
        Pair<Dimension, Integer> nMap = readNSet(fileReader, dimensionJsonObject);
        nSet.put(nMap.getKey(), nMap.getValue());
        return new PssType(SINGLE, nSet);

    }


    private static PssType readMultiPssType(JsonFileReader fileReader, JSONObject pssTypeJsonObject) throws ReaderException {
        Set<JSONObject> dimensionJsonObjects = fileReader.getJsonObjects(pssTypeJsonObject, dimensionKey);
        Map<Dimension, Integer> nSet = new HashMap<>();
        for (JSONObject dimensionObject : dimensionJsonObjects) {
            Pair<Dimension, Integer> nMap = readNSet(fileReader, dimensionObject);
            nSet.put(nMap.getKey(), nMap.getValue());
        }
        return new PssType(MULTI, nSet);
    }

    private static Pair<Dimension, Integer> readNSet(JsonFileReader fileReader, JSONObject dimensionJsonObject) throws ReaderException {

        Dimension dimension = readDimension(fileReader, dimensionJsonObject);
        int n = fileReader.getInt(dimensionJsonObject, nString);
        return new Pair<>(dimension, n);
    }

    private static Dimension readDimension(JsonFileReader fileReader, JSONObject dimensionObject) throws ReaderException {
        int dimensionId = fileReader.getInt(dimensionObject, idKey);
        String dimensionName = fileReader.getString(dimensionObject, nameKey);
        if (dimensionName.equals("location"))
            return new Dimension(dimensionId, dimensionName, true);
        else
            return new Dimension(dimensionId, dimensionName, false);
    }
}
