package pss.config.task;

import pss.data.anonymity.Anonymity;

public class RandomAnonymityGenerationConfig extends AnonymityGenerationConfig {
    protected final Anonymity lowerBoundAnonymity;
    protected final Anonymity upperBoundAnonymity;

    public RandomAnonymityGenerationConfig(Anonymity lowerBoundAnonymity, Anonymity upperBoundAnonymity) {
        super(AnonymityGenerationMethod.RANDOM);
        this.lowerBoundAnonymity = lowerBoundAnonymity;
        this.upperBoundAnonymity = upperBoundAnonymity;
    }

    public Anonymity getLowerBoundAnonymity() {
        return lowerBoundAnonymity;
    }

    public Anonymity getUpperBoundAnonymity() {
        return upperBoundAnonymity;
    }
}
