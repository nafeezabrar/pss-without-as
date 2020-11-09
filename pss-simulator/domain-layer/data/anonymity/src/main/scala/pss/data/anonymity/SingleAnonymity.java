package pss.data.anonymity;

public class SingleAnonymity extends Anonymity {
    protected final int anonymity;

    public SingleAnonymity(int anonymity) {
        this.anonymity = anonymity;
    }

    public int getValue() {
        return anonymity;
    }

    @Override
    public String toString() {
        return String.format("SingleAnonymity{anonymity=%d}", anonymity);
    }

    public int getAnonymity() {
        return anonymity;
    }
}
