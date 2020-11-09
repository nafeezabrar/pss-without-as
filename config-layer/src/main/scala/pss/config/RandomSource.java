package pss.config;

public abstract class RandomSource {
    protected final RandomSourceType randomSourceType;

    public RandomSource(RandomSourceType randomSourceType) {
        this.randomSourceType = randomSourceType;
    }

    public RandomSourceType getRandomSourceType() {
        return randomSourceType;
    }

    public enum RandomSourceType {
        INHERITED,
        GIVEN
    }
}

