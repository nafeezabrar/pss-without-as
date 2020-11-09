package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.StringToConfig;
import pss.data.dimension.Dimension;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static pss.config.ConfigKeyString.Anonymization.*;
import static pss.config.ConfigKeyString.dimensionKey;

public class JsonAnonymizedOoiReader {
    public static Map<Integer, Set<Integer>> readSingleAnonymizedOoiMap(JSONObject jsonObject, JsonFileReader jsonFileReader) {
        throw new UnsupportedOperationException("not impl");
    }

    public static Map<Integer, Map<Dimension, Set<Integer>>> readMultiAnonymizedOoiMap(JsonFileReader jsonFileReader, Set<Dimension> dimensions) throws ReaderException, InvalidConfigException {
        Set<JSONObject> anonymizedJsonObjects = jsonFileReader.getJsonObjects(anonymizedReportKey);
        Map<Integer, Map<Dimension, Set<Integer>>> anonymizedOoiMaps = new HashMap<>();
        for (JSONObject anonymizedJsonObject : anonymizedJsonObjects) {
            int reportId = jsonFileReader.getInt(anonymizedJsonObject, reportIdKey);
            Set<JSONObject> dimensionJsonObjects = jsonFileReader.getJsonObjects(anonymizedJsonObject, anonymizedLocalOoisKey);
            Map<Dimension, Set<Integer>> anonymizedOoiMap = new HashMap<>();
            for (JSONObject dimensionJsonObject : dimensionJsonObjects) {
                String dimensionString = jsonFileReader.getString(dimensionJsonObject, dimensionKey);
                Dimension dimension = StringToConfig.getDimensionFromString(dimensionString, dimensions);
                int[] anonymizedLocalOois = jsonFileReader.getIntArray(dimensionJsonObject, localOoisKey);
                Set<Integer> localOoiSet = new HashSet<>();
                for (int i = 0; i < anonymizedLocalOois.length; i++) {
                    localOoiSet.add(anonymizedLocalOois[i]);
                }
                anonymizedOoiMap.put(dimension, localOoiSet);
            }

            anonymizedOoiMaps.put(reportId, anonymizedOoiMap);
        }
        return anonymizedOoiMaps;
    }
}
