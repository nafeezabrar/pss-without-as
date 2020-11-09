package pss.factory.report.generation.seed.generation;

import pss.config.seed.FixedSeedGenerationConfig;
import pss.config.seed.RandomSeedGenerationConfig;
import pss.config.seed.SeedGenerationConfig;
import pss.exception.PssException;
import pss.seed.generation.FixedSeedGenerator;
import pss.seed.generation.RandomSeedGenerator;
import pss.seed.generation.SeedGenerable;

import static pss.config.seed.SeedGenerationConfig.SeedGenerationType;

public class SeedGenerationFactory {
    public static SeedGenerable createSeedGenerator(SeedGenerationConfig seedGenerationConfig) throws PssException {
        SeedGenerationType seedGenerationType = seedGenerationConfig.getSeedGenerationType();
        switch (seedGenerationType) {
            case FIXED_SEED:
                return createFixedSeedGenerator((FixedSeedGenerationConfig) seedGenerationConfig);
            case RANDOM_GENERATED_SEEDS:
                return createRandomSeedGenerator((RandomSeedGenerationConfig) seedGenerationConfig);
            case PRIME_GENERATED_SEEDS:
                return createPrimeSeedGenerator();
        }
        throw new PssException(String.format("seed generator factory not matched with %s", seedGenerationType.toString()));
    }

    private static SeedGenerable createFixedSeedGenerator(FixedSeedGenerationConfig seedGenerationConfig) {
        long seed = seedGenerationConfig.getSeed();
        return new FixedSeedGenerator(seed);
    }

    private static SeedGenerable createRandomSeedGenerator(RandomSeedGenerationConfig seedGenerationConfig) {
        long startSeed = seedGenerationConfig.getStartSeed();
        int totalSeeds = seedGenerationConfig.getTotalSeeds();
        return new RandomSeedGenerator(startSeed, totalSeeds);
    }

    private static SeedGenerable createPrimeSeedGenerator() {
        throw new UnsupportedOperationException("not impl");
    }
}
