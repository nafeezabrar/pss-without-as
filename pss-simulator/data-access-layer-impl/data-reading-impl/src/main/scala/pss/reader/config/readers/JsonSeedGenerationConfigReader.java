package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.ConfigKeyString;
import pss.config.ConfigKeyString.SeedGeneration;
import pss.config.StringToConfig;
import pss.config.seed.FixedSeedGenerationConfig;
import pss.config.seed.RandomSeedGenerationConfig;
import pss.config.seed.SeedGenerationConfig;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import static pss.config.ConfigKeyString.SeedGeneration.seedGenerationConfigKey;
import static pss.config.ConfigKeyString.SeedGeneration.totalSeedKey;
import static pss.config.seed.SeedGenerationConfig.SeedGenerationType;

public class JsonSeedGenerationConfigReader {
    public static SeedGenerationConfig readSeedGenerationConfig(JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException, PssException {
        JSONObject seedGenerationJsonObject = jsonFileReader.getJsonObject(seedGenerationConfigKey);
        String seedGenerationTypeString = jsonFileReader.getString(seedGenerationJsonObject, SeedGeneration.seedGenerationType);
        SeedGenerationType seedGenerationType = StringToConfig.getSeedGenerationMethod(seedGenerationTypeString);
        switch (seedGenerationType) {
            case FIXED_SEED:
                return readFixedSeedGenerationConfig(seedGenerationJsonObject, jsonFileReader);
            case RANDOM_GENERATED_SEEDS:
                return readRandomSeedGenerationConfig(seedGenerationJsonObject, jsonFileReader);
            case PRIME_GENERATED_SEEDS:
                return readPrimeSeedGenerationConfig(seedGenerationJsonObject, jsonFileReader);
        }
        throw new PssException(String.format("seed generation type %s not matched", seedGenerationType.toString()));
    }

    private static SeedGenerationConfig readFixedSeedGenerationConfig(JSONObject seedGenerationJsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        long seed = jsonFileReader.getLong(seedGenerationJsonObject, ConfigKeyString.seed);
        return new FixedSeedGenerationConfig(seed);
    }

    private static SeedGenerationConfig readRandomSeedGenerationConfig(JSONObject seedGenerationJsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        int totalSeeds = jsonFileReader.getInt(seedGenerationJsonObject, totalSeedKey);
        long startSeed = jsonFileReader.getLong(seedGenerationJsonObject, ConfigKeyString.seed);
        return new RandomSeedGenerationConfig(totalSeeds, startSeed);
    }

    private static SeedGenerationConfig readPrimeSeedGenerationConfig(JSONObject seedGenerationJsonObject, JsonFileReader jsonFileReader) {
        throw new UnsupportedOperationException("not impl");
    }
}
