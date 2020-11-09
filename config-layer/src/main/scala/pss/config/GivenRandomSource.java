package pss.config;

public class GivenRandomSource extends RandomSource {
    protected final long seed;

    public GivenRandomSource(long seed) {
        super(RandomSourceType.GIVEN);
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }
}
