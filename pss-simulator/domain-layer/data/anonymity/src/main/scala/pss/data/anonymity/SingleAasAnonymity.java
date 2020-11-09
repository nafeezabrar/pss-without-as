package pss.data.anonymity;

public class SingleAasAnonymity extends SingleAnonymity {
    protected final int addingAnonymity;

    public SingleAasAnonymity(int anonymity, int addingAnonymity) {
        super(anonymity);
        this.addingAnonymity = addingAnonymity;
    }

    public int getAddingAnonymity() {
        return addingAnonymity;
    }
}
