package pss.reader.config.readers;

import com.pss.adversary.Adversary.AdversaryType;
import com.pss.adversary.ApsAdversaryType;
import com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import com.pss.adversary.MultiUserApsAdversaryType;
import com.pss.adversary.RandomUserApsAdversaryType;
import com.pss.adversary.SingleUserApsAdversaryType;
import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.adversary.AdversaryConfig;
import pss.config.adversary.ApsAdversaryConfig;
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

import static pss.config.ConfigKeyString.Adversary.*;

public class JsonAdversaryConfigReader {
    public static AdversaryConfig readAdversaryConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException, PssException {
        JSONObject jsonObject = fileReader.getJsonObject(ConfigKeyString.Adversary.adversaryConfigKey);
        AdversaryType adversaryType = JsonAdversaryTypeReader.readAdversaryTypeConfig(jsonObject, fileReader);
        switch (adversaryType) {
            case APS_ADVERSARY:
                return readApsAdversaryConfig(adversaryType, jsonObject, fileReader);
        }
        throw new InvalidConfigException(String.format("adversary type %s not matched", adversaryType.toString()));
    }

    private static AdversaryConfig readApsAdversaryConfig(AdversaryType adversaryType, JSONObject jsonObject, JsonFileReader fileReader) throws InvalidConfigException, ReaderException, PssException {
        DeanonymizationConfig deanonymizationConfig = JsonDeanonymizationConfigReader.readDeanonymimzationConfig(jsonObject, fileReader);
        ReportFilteringConfig reportFilteringConfig = JsonReportFilteringConfigReader.readReportFilteringConfig(jsonObject, fileReader);
        String adversaryStrengthString = fileReader.getString(jsonObject, adversaryStrengthKey);
        ApsAdversaryUserStrength apsAdversaryUserStrength = StringToConfig.getApsAdversaryType(adversaryStrengthString);
        ApsAdversaryType apsAdversaryType = readApsAdversaryType(jsonObject, fileReader, apsAdversaryUserStrength);
        SaveConfig saveConfig = JsonSaveConfigReader.readSaveConfig(jsonObject, fileReader);
        PrintConfig printConfig = JsonPrintConfigReader.readPrintConfig(jsonObject, fileReader);
        DecodabilityCalculationConfig decodabilityCalculationConfig = JsonDecodabilityCalculationConfigReader.readDecodabilityCalculationConfig(jsonObject, fileReader);
        return new ApsAdversaryConfig(adversaryType, saveConfig, printConfig, decodabilityCalculationConfig, deanonymizationConfig, reportFilteringConfig, apsAdversaryType);
    }

    private static ApsAdversaryType readApsAdversaryType(JSONObject jsonObject, JsonFileReader fileReader, ApsAdversaryUserStrength apsAdversaryUserStrength) throws PssException, ReaderException {
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                return readSingleUserApsAdversaryType(jsonObject, fileReader);
            case DISGUISED_AS_MULTI_USER:
                return readMultiUserApsAdversaryType(jsonObject, fileReader);
            case DISGUISED_AS_RANDOM_USER:
                return readRandomUserApsAdversaryType(jsonObject, fileReader);

        }
        throw new PssException(String.format("aps adversary with user strength %s not matched", apsAdversaryUserStrength.toString()));
    }

    private static ApsAdversaryType readSingleUserApsAdversaryType(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException {
        int userId = fileReader.getInt(jsonObject, adversaryUserId);
        return new SingleUserApsAdversaryType(userId);
    }

    private static ApsAdversaryType readMultiUserApsAdversaryType(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException {
        Set<Integer> userIdSet = new HashSet<>();
        int[] userIds = fileReader.getIntArray(jsonObject, adversaryUserId);
        for (Integer userId : userIds)
            userIdSet.add(userId);
        return new MultiUserApsAdversaryType(userIdSet);
    }

    private static ApsAdversaryType readRandomUserApsAdversaryType(JSONObject jsonObject, JsonFileReader fileReader) throws ReaderException {
        int adversaryUserNumbers = fileReader.getInt(jsonObject, adversaryUserNumber);
        return new RandomUserApsAdversaryType(adversaryUserNumbers);
    }
}
