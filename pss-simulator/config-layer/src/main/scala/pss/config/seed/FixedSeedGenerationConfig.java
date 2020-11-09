package pss.config.seed;

import static pss.config.seed.SeedGenerationConfig.SeedGenerationType.FIXED_SEED;

public class FixedSeedGenerationConfig extends SeedGenerationConfig {
    protected final long seed;

    public FixedSeedGenerationConfig(long seed) {
        super(FIXED_SEED);
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }
}
