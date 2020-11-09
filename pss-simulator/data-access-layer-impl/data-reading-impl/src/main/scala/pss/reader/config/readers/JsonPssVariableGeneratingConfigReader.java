package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.RandomSource;
import pss.config.StringToConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.data.dimension.Dimension;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static pss.config.ConfigKeyString.*;
import static pss.config.task.PssVariableGenerationConfig.OoiGenerationMethod;

public class JsonPssVariableGeneratingConfigReader {
    public static PssVariableGenerationConfig readPssVariableGenerationConfig(JsonFileReader fileReader, PssType pssType) throws ReaderException, InvalidConfigException, PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        JSONObject jsonObject = fileReader.getJsonObject(ConfigKeyString.PsvGeneration.pssVariableGenerationConfigKey);
        RandomSource randomSource = JsonRandomSourceReader.readRandomSource(jsonObject, fileReader);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, fileReader);

        switch (pssDimensionType) {
            case SINGLE:
                return readSinglePssVariableGenerationConfig(fileReader, jsonObject, pssType, randomSource, saveConfig, printConfig);
            case MULTI:
                return readMultiPssVariableGenerationConfig(fileReader, jsonObject, pssType, randomSource, saveConfig, printConfig);
        }
        throw new PssException(format("pss dimension %s not matched", pssDimensionType.toString()));

    }

    private static PssVariableGenerationConfig readSinglePssVariableGenerationConfig(JsonFileReader fileReader, JSONObject jsonObject, PssType pssType, RandomSource randomSource, SaveConfig saveConfig, PrintConfig printConfig) throws ReaderException, InvalidConfigException, PssException {
        String methodString = fileReader.getString(jsonObject, method);
        OoiGenerationMethod ooiGenerationMethod = StringToConfig.getPssVariableGenerationMethod(methodString);
        Map<Dimension, OoiGenerationMethod> pssVariableGenerationConfigMap = new HashMap<>();
        pssVariableGenerationConfigMap.put(pssType.getDimension(), ooiGenerationMethod);
        String ooiValueGenerationMethodString = fileReader.getString(jsonObject, ConfigKeyString.PsvGeneration.ooiValueGenerationMethodKey);
        PssVariableGenerationConfig.OoiValueGenerationMethod ooiValueGenerationMethod = StringToConfig.getOoiValueGenerationMethod(ooiValueGenerationMethodString);
        return new PssVariableGenerationConfig(pssVariableGenerationConfigMap, ooiValueGenerationMethod, randomSource, saveConfig, printConfig);

    }

    private static PssVariableGenerationConfig readMultiPssVariableGenerationConfig(JsonFileReader fileReader, JSONObject jsonObject, PssType pssType, RandomSource randomSource, SaveConfig saveConfig, PrintConfig printConfig) throws ReaderException, InvalidConfigException {
        Set<JSONObject> dimensionJsonObjects = fileReader.getJsonObjects(jsonObject, dimensionKey);
        Set<Dimension> dimensionSet = pssType.getDimensionSet();
        Map<Dimension, OoiGenerationMethod> pssVariableGenerationMethodMap = new HashMap<>();
        for (JSONObject dimensionJsonObject : dimensionJsonObjects) {
            String dimensionString = fileReader.getString(dimensionJsonObject, nameKey);
            Dimension dimension = StringToConfig.getDimensionFromString(dimensionString, dimensionSet);
            String methodString = fileReader.getString(dimensionJsonObject, method);
            OoiGenerationMethod ooiGenerationMethod = StringToConfig.getPssVariableGenerationMethod(methodString);
            pssVariableGenerationMethodMap.put(dimension, ooiGenerationMethod);

        }
        String ooiValueGenerationMethodString = fileReader.getString(jsonObject, ConfigKeyString.PsvGeneration.ooiValueGenerationMethodKey);
        PssVariableGenerationConfig.OoiValueGenerationMethod ooiValueGenerationMethod = StringToConfig.getOoiValueGenerationMethod(ooiValueGenerationMethodString);
        return new PssVariableGenerationConfig(pssVariableGenerationMethodMap, ooiValueGenerationMethod, randomSource, saveConfig, printConfig);
    }
}
