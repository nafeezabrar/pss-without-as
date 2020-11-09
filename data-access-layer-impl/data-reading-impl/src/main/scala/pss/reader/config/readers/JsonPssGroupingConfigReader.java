package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.ConfigKeyString.PssGrouping;
import pss.config.StringToConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.MultiPssGroupingConfigOneDimension;
import pss.config.task.PssGroupingConfig;
import pss.config.task.PssGroupingConfig.PssGroupingMethod;
import pss.data.dimension.Dimension;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static pss.config.ConfigKeyString.PssGrouping.*;
import static pss.config.ConfigKeyString.numberKey;
import static pss.config.task.PssGroupingConfig.PssGroupingMethod.ONE_DIMENSION_GROUPING;

public class JsonPssGroupingConfigReader {
    public static PssGroupingConfig readPssGroupingConfig(JsonFileReader fileReader, PssType pssType) throws ReaderException, InvalidConfigException, PssException {
        JSONObject jsonObject = fileReader.getJsonObject(PssGrouping.pssGroupingConfigKey);
        boolean shouldPrintOoiMapper = fileReader.containsKey(jsonObject, printOoiMapperKey);

        switch (pssType.getPssDimensionType()) {
            case SINGLE:
                return readSinglePssGroupingConfig(fileReader, jsonObject, pssType.getDimension(), shouldPrintOoiMapper);
            case MULTI:
                return readMultiPssGroupingConfig(fileReader, jsonObject, pssType.getDimensionSet(), shouldPrintOoiMapper);
        }
        throw new PssException("Pss type not recognized while reading pss grouping config via JSON");
    }

    private static PssGroupingConfig readSinglePssGroupingConfig(JsonFileReader fileReader, JSONObject jsonObject, Dimension dimension, boolean shouldPrintOoiMapper) throws ReaderException, PssException, InvalidConfigException {
        JSONObject ooiInEachGroupJsonObject = fileReader.getJsonObject(jsonObject, ooiInEachGroupKey);
        String dimensionString = fileReader.getString(ooiInEachGroupJsonObject, ConfigKeyString.dimensionKey);
        if (!dimension.getName().equals(dimensionString)) {
            throw new PssException(String.format("%s dimension is not matched with current dimension %s", dimensionString, dimension.getName()));
        }
        int ooiInEachGroup = fileReader.getInt(ooiInEachGroupJsonObject, numberKey);
        Map<Dimension, Integer> ooiInEachGroupMap = new HashMap<>();
        ooiInEachGroupMap.put(dimension, ooiInEachGroup);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, fileReader);

        return new PssGroupingConfig(ONE_DIMENSION_GROUPING, ooiInEachGroupMap, saveConfig, printConfig, shouldPrintOoiMapper);
    }

    private static PssGroupingConfig readMultiPssGroupingConfig(JsonFileReader fileReader, JSONObject jsonObject, Set<Dimension> dimensionSet, boolean shouldPrintOoiMapper) throws ReaderException, InvalidConfigException {
        Set<JSONObject> jsonObjects = fileReader.getJsonObjects(jsonObject, ooiInEachGroupKey);
        Map<Dimension, Integer> ooiInEachGroupMap = new HashMap<>();
        for (JSONObject ooiInEachGroupJsonObject : jsonObjects) {
            String dimensionString = fileReader.getString(ooiInEachGroupJsonObject, ConfigKeyString.dimensionKey);
            int ooiInEachGroup = fileReader.getInt(ooiInEachGroupJsonObject, numberKey);
            Dimension dimension = StringToConfig.getDimensionFromString(dimensionString, dimensionSet);
            ooiInEachGroupMap.put(dimension, ooiInEachGroup);
        }
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, fileReader);

        String pssGroupingMethodString = fileReader.getString(jsonObject, ConfigKeyString.method);
        PssGroupingMethod pssGroupingMethod = StringToConfig.getPssGroupingMethod(pssGroupingMethodString);

        switch (pssGroupingMethod) {
            case ONE_DIMENSION_GROUPING:
                String dimensionString = fileReader.getString(jsonObject, dividingDimensionKey);
                Dimension dividingDimension = StringToConfig.getDimensionFromString(dimensionString, dimensionSet);
                return new MultiPssGroupingConfigOneDimension(pssGroupingMethod, ooiInEachGroupMap, saveConfig, printConfig, shouldPrintOoiMapper, dividingDimension);
            case ALL_DIMENSION_GROUPING:
                return new PssGroupingConfig(pssGroupingMethod, ooiInEachGroupMap, saveConfig, printConfig, shouldPrintOoiMapper);
            case ALL_DIMENSION_SEQUENTIAL_GROUPING:
                return new PssGroupingConfig(pssGroupingMethod, ooiInEachGroupMap, saveConfig, printConfig, shouldPrintOoiMapper);
        }
        throw new InvalidConfigException(String.format("invalid pss grouping method %s", pssGroupingMethod.toString()));
    }
}
