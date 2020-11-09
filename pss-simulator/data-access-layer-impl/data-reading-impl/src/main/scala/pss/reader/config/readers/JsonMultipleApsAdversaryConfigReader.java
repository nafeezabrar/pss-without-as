package pss.reader.config.readers;

import com.pss.adversary.Adversary.AdversaryType;
import com.pss.adversary.ApsAdversaryType;
import com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import com.pss.adversary.MultiUserApsAdversaryType;
import com.pss.adversary.SingleUserApsAdversaryType;
import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.adversary.MultipleApsAdversaryConfig;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.PrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.task.DeanonymizationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.config.decodability.calculator.JsonDecodabilityCalculationConfigReader;
import pss.reader.json.JsonFileReader;

import java.util.HashSet;
import java.util.Set;

import static pss.config.ConfigKeyString.Adversary.adversaryGenerationType;
import static pss.config.ConfigKeyString.Adversary.adversaryUserId;
import static pss.config.adversary.MultipleApsAdversaryConfig.ApsAdversaryGenerationType;

public class JsonMultipleApsAdversaryConfigReader {
    public static MultipleApsAdversaryConfig readAdversaryConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException, PssException {
        JSONObject jsonObject = fileReader.getJsonObject(ConfigKeyString.Adversary.adversaryConfigKey);
        AdversaryType adversaryType = JsonAdversaryTypeReader.readAdversaryTypeConfig(jsonObject, fileReader);
        switch (adversaryType) {
            case APS_ADVERSARY:
                return readApsAdversaryConfig(adversaryType, jsonObject, fileReader);
        }
        throw new InvalidConfigException(String.format("adversary type %s not matched", adversaryType.toString()));
    }

    private static MultipleApsAdversaryConfig readApsAdversaryConfig(AdversaryType adversaryType, JSONObject jsonObject, JsonFileReader fileReader) throws InvalidConfigException, ReaderException, PssException {
        DeanonymizationConfig deanonymizationConfig = JsonDeanonymizationConfigReader.readDeanonymimzationConfig(jsonObject, fileReader);
        ReportFilteringConfig reportFilteringConfig = JsonReportFilteringConfigReader.readReportFilteringConfig(jsonObject, fileReader);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, fileReader);
        DecodabilityCalculationConfig decodabilityCalculationConfig = JsonDecodabilityCalculationConfigReader.readDecodabilityCalculationConfig(jsonObject, fileReader);
        String apsAdversaryGenerationTypeString = fileReader.getString(jsonObject, adversaryGenerationType);
        ApsAdversaryGenerationType generationType = StringToConfig.getAdversaryGenerationType(apsAdversaryGenerationTypeString);
        return new MultipleApsAdversaryConfig(saveConfig, printConfig, decodabilityCalculationConfig, deanonymizationConfig, reportFilteringConfig, generationType);
    }

    private static ApsAdversaryType readApsAdversaryType(JSONObject jsonObject, JsonFileReader fileReader, ApsAdversaryUserStrength apsAdversaryUserStrength) throws PssException, ReaderException {
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                return readSingleUserApsAdversaryType(jsonObject, fileReader);

            case DISGUISED_AS_MULTI_USER:
                return readMultiUserApsAdversaryType(jsonObject, fileReader);
        }
        throw new PssException(String.format("aps adversary with user strength %s not matched", apsAdversaryUserStrength.toString()));
    }

    private static ApsAdversaryType readSingleUserApsAdversaryType(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException {
        int userId = fileReader.getInt(jsonObject, adversaryUserId);
        return new SingleUserApsAdversaryType(userId);
    }

    private static ApsAdversaryType readMultiUserApsAdversaryType(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException {
        int[] userIds = fileReader.getIntArray(jsonObject, adversaryUserId);
        Set<Integer> userIdSet = new HashSet<>();
        for (Integer userId : userIds)
            userIdSet.add(userId);
        return new MultiUserApsAdversaryType(userIdSet);
    }
}
