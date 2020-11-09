package pss.saving.OcTableSavable;

import pss.data.oc_table.OcTable;

import java.io.IOException;

public interface OcTableSavable<TOcTable extends OcTable> {
    void saveOcTable(TOcTable ocTable) throws IOException, ClassNotFoundException;
}
