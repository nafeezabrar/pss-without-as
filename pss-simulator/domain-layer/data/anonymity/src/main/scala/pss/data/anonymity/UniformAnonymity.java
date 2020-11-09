package pss.data.anonymity;

import java.util.List;

public class UniformAnonymity extends Anonymity {
    protected final List<Anonymity> anonymities;

    public UniformAnonymity(List<Anonymity> anonymities) {
        this.anonymities = anonymities;
    }

    public List<Anonymity> getAnonymities() {
        return anonymities;
    }
}
