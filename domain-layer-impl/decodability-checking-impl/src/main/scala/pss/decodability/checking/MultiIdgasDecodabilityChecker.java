package pss.decodability.checking;

import pss.data.oc_table.MultiOcTable;

public abstract class MultiIdgasDecodabilityChecker implements MultiDecodabilityChecker {
    protected final MultiOcTable ocTable;

    public MultiIdgasDecodabilityChecker(MultiOcTable ocTable) {
        this.ocTable = ocTable;
    }
}
