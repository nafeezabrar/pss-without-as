package pss.seed.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSeedGenerator implements SeedGenerable {
    protected final long startSeed;
    protected final int totalSeeds;

    public RandomSeedGenerator(long startSeed, int totalSeeds) {
        this.startSeed = startSeed;
        this.totalSeeds = totalSeeds;
    }

    @Override
    public List<Long> generateSeeds() {
        List<Long> seeds = new ArrayList<>();
        Random random = new Random(startSeed);
        for (int i = 0; i < totalSeeds; i++) {
            seeds.add((long) random.nextInt(Integer.SIZE - 1));
        }
        return seeds;
    }
}
