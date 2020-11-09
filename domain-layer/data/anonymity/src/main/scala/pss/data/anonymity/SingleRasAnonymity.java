package pss.data.anonymity;

public class SingleRasAnonymity extends SingleAnonymity {
    protected final int replacingAnonymity;

    public SingleRasAnonymity(int anonymity, int replacingAnonymity) {
        super(anonymity);
        this.replacingAnonymity = replacingAnonymity;
    }

    public int getReplacingAnonymity() {
        return replacingAnonymity;
    }
}
