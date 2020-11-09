package pss.config.task;

import pss.data.anonymity.Anonymity;

public class FixedAnonymityGenerationConfig extends AnonymityGenerationConfig {
    protected final Anonymity anonymity;

    public FixedAnonymityGenerationConfig(Anonymity anonymity) {
        super(AnonymityGenerationMethod.FIXED);
        this.anonymity = anonymity;
    }

    public Anonymity getAnonymity() {
        return anonymity;
    }
}
