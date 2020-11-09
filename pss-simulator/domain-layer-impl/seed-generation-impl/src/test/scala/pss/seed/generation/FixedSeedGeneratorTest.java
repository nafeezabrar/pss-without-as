package pss.seed.generation;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FixedSeedGeneratorTest {
    private final long seed = 2;
    private FixedSeedGenerator fixedSeedGenerator;

    @Before
    public void setUp() throws Exception {
        fixedSeedGenerator = new FixedSeedGenerator(seed);
    }

    @Test
    public void generateSeed() throws Exception {
        List<Long> seeds = fixedSeedGenerator.generateSeeds();
        assertEquals(1, seeds.size());
        Long generatedSeed = seeds.get(0);
        assertTrue(seed == generatedSeed);
    }

}