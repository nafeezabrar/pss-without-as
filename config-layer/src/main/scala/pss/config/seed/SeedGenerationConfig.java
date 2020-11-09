package pss.config.seed;

public abstract class SeedGenerationConfig {
    protected final SeedGenerationType seedGenerationType;

    public SeedGenerationConfig(SeedGenerationType seedGenerationType) {
        this.seedGenerationType = seedGenerationType;
    }

    public SeedGenerationType getSeedGenerationType() {
        return seedGenerationType;
    }

    public enum SeedGenerationType {
        FIXED_SEED,
        RANDOM_GENERATED_SEEDS,
        PRIME_GENERATED_SEEDS
    }
}
