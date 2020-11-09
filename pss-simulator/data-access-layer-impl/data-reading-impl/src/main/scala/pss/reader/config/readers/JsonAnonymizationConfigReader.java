package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.ReadingSourceType;
import pss.config.ConfigKeyString;
import pss.config.StringToConfig;
import pss.config.task.AnonymizationConfig;
import pss.config.task.AnonymizationConfig.AnonymizationMethod;
import pss.config.task.ManualIdgasAnonymizationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static java.lang.String.format;
import static pss.config.ConfigKeyString.fileNameKey;
import static pss.config.ConfigKeyString.readingSourceKey;

public class JsonAnonymizationConfigReader {
    public static AnonymizationConfig readPssGroupingConfig(JsonFileReader fileReader) throws ReaderException, InvalidConfigException {
        JSONObject jsonObject = fileReader.getJsonObject(ConfigKeyString.Anonymization.anonymizationConfigKey);
        String anonymizaitonMethodString = fileReader.getString(jsonObject, ConfigKeyString.method);
        AnonymizationMethod anonymizationMethod = StringToConfig.getAnonymizationMethod(anonymizaitonMethodString);
        switch (anonymizationMethod) {
            case IDGAS:
                return new AnonymizationConfig(anonymizationMethod);
            case IDGAS_FROM_FILE:
                return readIdgasManualAnonymizationConfig(fileReader, jsonObject);
            case AAS:
                return new AnonymizationConfig(anonymizationMethod);
            case RAS:
                return new AnonymizationConfig(anonymizationMethod);
            case DGAS:
                return new AnonymizationConfig(anonymizationMethod);
        }
        throw new InvalidConfigException(format("Anonymization conifg %s not matched", anonymizationMethod.toString()));
    }

    private static AnonymizationConfig readIdgasManualAnonymizationConfig(JsonFileReader fileReader, JSONObject jsonObject) throws ReaderException, InvalidConfigException {
        String readingSourceTypeString = fileReader.getString(jsonObject, readingSourceKey);
        ReadingSourceType readingSourceType = StringToConfig.getSourceType(readingSourceTypeString);
        String fileName = fileReader.getString(jsonObject, fileNameKey);
        return new ManualIdgasAnonymizationConfig(readingSourceType, fileName);
    }
}
