package pss.reader.config.decodability.calculator;

import org.json.simple.JSONObject;
import pss.config.ConfigToString;
import pss.config.StringToConfig;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.DecodabilityPrintConfig;
import pss.data.decodability.DecodabilityCalculationPeriod;
import pss.data.decodability.DecodabilityType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.readers.JsonDecodabilityPrintConfigReader;
import pss.reader.json.JsonFileReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static pss.config.ConfigKeyString.DecodabilityCalculation.decodabilityCalculationConfigKey;
import static pss.config.ConfigKeyString.DecodabilityCalculation.decodabilityType;

public class JsonDecodabilityCalculationConfigReader {
    public static DecodabilityCalculationConfig readDecodabilityCalculationConfig(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException, InvalidConfigException, PssException {
        if (!fileReader.containsKey(jsonObject, decodabilityCalculationConfigKey)) {
            return new DecodabilityCalculationConfig(new HashMap<>(), new HashMap<>());
        }
        JSONObject calculationConfigJsonObject = fileReader.getJsonObject(jsonObject, decodabilityCalculationConfigKey);
        Map<DecodabilityCalculationPeriod, Set<DecodabilityType>> decodabilityMap = new HashMap<>();
        Map<DecodabilityCalculationPeriod, DecodabilityPrintConfig> decodabilityPrintConfigMap = new HashMap<>();
        for (DecodabilityCalculationPeriod calculationPeriod : DecodabilityCalculationPeriod.values()) {
            String decodabilityCalculationPeriodString = ConfigToString.getDecodabilityCalculationPeriodString(calculationPeriod);
            boolean containsPeriod = fileReader.containsKey(calculationConfigJsonObject, decodabilityCalculationPeriodString);
            if (containsPeriod) {
                JSONObject decodabilityPeriodJsonObject = fileReader.getJsonObject(calculationConfigJsonObject, decodabilityCalculationPeriodString);
                DecodabilityPrintConfig decodabilityPrintConfig = JsonDecodabilityPrintConfigReader.readPrintConfig(decodabilityPeriodJsonObject, fileReader);
                decodabilityPrintConfigMap.put(calculationPeriod, decodabilityPrintConfig);
                Set<DecodabilityType> decodabilityTypes = getDecodabilityTypes(fileReader, decodabilityPeriodJsonObject);
                decodabilityMap.put(calculationPeriod, decodabilityTypes);
            }
        }
        return new DecodabilityCalculationConfig(decodabilityMap, decodabilityPrintConfigMap);
    }

    private static Set<DecodabilityType> getDecodabilityTypes(JsonFileReader fileReader, JSONObject decodabilityPeriodJsonObject) throws ReaderException, InvalidConfigException, PssException {
        Set<DecodabilityType> decodabilityTypes = new HashSet<>();
        String[] decodabilityTypesString = fileReader.getStringArray(decodabilityPeriodJsonObject, decodabilityType);
        for (String decodabilityTypeString : decodabilityTypesString) {
            DecodabilityType decodabilityType = StringToConfig.getDecodabilityType(decodabilityTypeString);
            decodabilityTypes.add(decodabilityType);
        }

        return decodabilityTypes;
    }

}
