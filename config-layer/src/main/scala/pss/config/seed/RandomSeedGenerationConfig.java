package pss.config.seed;

import static pss.config.seed.SeedGenerationConfig.SeedGenerationType.RANDOM_GENERATED_SEEDS;

public class RandomSeedGenerationConfig extends SeedGenerationConfig {
    protected final int totalSeeds;
    protected final long startSeed;

    public RandomSeedGenerationConfig(int totalSeeds, long startSeed) {
        super(RANDOM_GENERATED_SEEDS);
        this.totalSeeds = totalSeeds;
        this.startSeed = startSeed;
    }

    public int getTotalSeeds() {
        return totalSeeds;
    }

    public long getStartSeed() {
        return startSeed;
    }
}
