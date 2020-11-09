package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.ConfigKeyString.AnonymityGeneration;
import pss.config.RandomSource;
import pss.config.StringToConfig;
import pss.config.task.*;
import pss.config.task.AnonymityGenerationConfig.AnonymityGenerationMethod;
import pss.data.anonymity.Anonymity;
import pss.data.anonymity.UniformAnonymity;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.config.factory.reader.JsonAnonymityReaderFactory;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.AnonymityGeneration.*;
import static pss.config.ConfigKeyString.method;

public class JsonAnonymityGenerationConfigReader {
    public static AnonymityGenerationConfig readAnonymityGenerationConfig(PssType pssType, JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        JSONObject anonymityJsonObject = jsonFileReader.getJsonObject(AnonymityGeneration.anonymityGenerationConfigKey);
        String anonymityGenerationMethodString = jsonFileReader.getString(anonymityJsonObject, method);
        AnonymityGenerationMethod anonymityGenerationMethod = StringToConfig.getAnonymityGenerationMethod(anonymityGenerationMethodString);
        JsonAnonymityReader anonymityReader = JsonAnonymityReaderFactory.getJsonAnonymityReader(pssType, anonymityJsonObject, jsonFileReader, anonymityGenerationMethod);
        switch (anonymityGenerationMethod) {
            case FIXED:
                return readFixedAnonymityGenerationConfig(anonymityReader);
            case FIXED_AAS:
                return readFixedAasAnonymityGenerationConfig(anonymityReader);
            case FIXED_RAS:
                return readFixedRasAnonymityGenerationConfig(anonymityReader);
            case RANDOM:
                return readRandomAnonymityGenerationConfig(anonymityReader);
            case FROM_FILE:
                return readFromFileAnonymityGenerationConfig(jsonFileReader, anonymityJsonObject);
            case FROM_FILE_INHERITED:
                return readFromFileInheritedAnonymityGenerationConfig();
            case UNIFORM_ANONYMITY:
                RandomSource randomSource = JsonRandomSourceReader.readRandomSource(anonymityJsonObject, jsonFileReader);
                return readUniformAnonimityGenerationConfig(anonymityReader, randomSource);
        }
        throw new InvalidConfigException(String.format("Reader not implemented for %s Anonymity Generation Method", anonymityGenerationMethod.toString()));
    }

    private static AnonymityGenerationConfig readFixedRasAnonymityGenerationConfig(JsonAnonymityReader anonymityReader) throws InvalidConfigException, ReaderException {
        Anonymity anonymity = anonymityReader.readAnonymityConfig(anonymityKey);
        return new FixedAnonymityGenerationConfig(anonymity);
    }

    private static AnonymityGenerationConfig readUniformAnonimityGenerationConfig(JsonAnonymityReader anonymityReader, RandomSource randomSource) throws InvalidConfigException, ReaderException {
        Anonymity anonymity = anonymityReader.readAnonymityConfig(anonymityKey);
        return new UniformAnonymityGenerationConfig((UniformAnonymity) anonymity, randomSource);
    }

    private static AnonymityGenerationConfig readFixedAasAnonymityGenerationConfig(JsonAnonymityReader anonymityReader) throws InvalidConfigException, ReaderException {
        Anonymity anonymity = anonymityReader.readAnonymityConfig(anonymityKey);
        return new FixedAnonymityGenerationConfig(anonymity);
    }

    private static AnonymityGenerationConfig readFixedAnonymityGenerationConfig(JsonAnonymityReader anonymityReader) throws ReaderException, InvalidConfigException {
        Anonymity anonymity = anonymityReader.readAnonymityConfig(anonymityKey);
        return new FixedAnonymityGenerationConfig(anonymity);
    }

    private static AnonymityGenerationConfig readRandomAnonymityGenerationConfig(JsonAnonymityReader anonymityReader) throws ReaderException, InvalidConfigException {
        Anonymity lowerAnonymity = anonymityReader.readAnonymityConfig(anonymityLowerBoundKey);
        Anonymity upperAnonymity = anonymityReader.readAnonymityConfig(anonymityUpperBoundKey);
        return new RandomAnonymityGenerationConfig(lowerAnonymity, upperAnonymity);
    }

    private static AnonymityGenerationConfig readFromFileAnonymityGenerationConfig(JsonFileReader fileReader, JSONObject jsonObject) throws ReaderException {
        String fileName = fileReader.getString(jsonObject, ConfigKeyString.fileNameKey);
        return new FromFileAnonymityGenerationConfig(fileName);
    }

    private static AnonymityGenerationConfig readFromFileInheritedAnonymityGenerationConfig() {
        return new FromFileInheritedAnonymityGenerationConfig();
    }
}
