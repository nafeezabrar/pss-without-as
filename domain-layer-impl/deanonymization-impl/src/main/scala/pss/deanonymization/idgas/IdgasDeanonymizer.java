package pss.deanonymization.idgas;

import pss.data.oc_table.OcTable;

public class IdgasDeanonymizer<TOcTable extends OcTable> {
    protected final TOcTable ocTable;

    public IdgasDeanonymizer(TOcTable ocTable) {
        this.ocTable = ocTable;
    }

    public TOcTable getOcTable() {
        return ocTable;
    }
}
