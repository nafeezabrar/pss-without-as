package pss.config.task;

import pss.config.RandomSource;
import pss.data.anonymity.UniformAnonymity;

public class UniformAnonymityGenerationConfig extends AnonymityGenerationConfig {
    protected final UniformAnonymity anonymity;
    protected final RandomSource randomSource;

    public UniformAnonymityGenerationConfig(UniformAnonymity anonymity, RandomSource randomSource) {
        super(AnonymityGenerationMethod.UNIFORM_ANONYMITY);
        this.anonymity = anonymity;
        this.randomSource = randomSource;
    }

    public RandomSource getRandomSource() {
        return randomSource;
    }

    public UniformAnonymity getAnonymity() {
        return anonymity;
    }
}
