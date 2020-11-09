package pss.decodability.checking;


import pss.data.oc_table.SingleOcTable;

public abstract class SingleIdgasDecodabilityChecker implements SingleDecodabilityChecker {
    protected final SingleOcTable ocTable;

    public SingleIdgasDecodabilityChecker(SingleOcTable ocTable) {
        this.ocTable = ocTable;
    }
}
