package pss.seed.generation;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RandomSeedGeneratorTest {
    private final long startSeed = 0;
    private final int totalSeeds = 100;
    private RandomSeedGenerator randomSeedGenerator;

    @Before
    public void setUp() throws Exception {
        randomSeedGenerator = new RandomSeedGenerator(startSeed, totalSeeds);
    }

    @Test
    public void generateSeeds() throws Exception {
        List<Long> seeds = randomSeedGenerator.generateSeeds();
        assertEquals(totalSeeds, seeds.size());
        long anotherStartSeed = this.startSeed + 1;
        randomSeedGenerator = new RandomSeedGenerator(anotherStartSeed, totalSeeds);
        List<Long> otherSeeds = randomSeedGenerator.generateSeeds();
        assertNotEquals(otherSeeds.get(0), seeds.get(0));
    }

}