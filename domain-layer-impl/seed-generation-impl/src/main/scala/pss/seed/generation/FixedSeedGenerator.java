package pss.seed.generation;

import java.util.ArrayList;
import java.util.List;

public class FixedSeedGenerator implements SeedGenerable {
    protected final long seed;

    public FixedSeedGenerator(long seed) {
        this.seed = seed;
    }

    @Override
    public List<Long> generateSeeds() {
        List<Long> seeds = new ArrayList<>();
        seeds.add(seed);
        return seeds;
    }
}
