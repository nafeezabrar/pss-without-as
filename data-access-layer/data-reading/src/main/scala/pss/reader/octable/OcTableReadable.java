package pss.reader.octable;

import pss.data.oc_table.OcTable;

import java.io.IOException;

public interface OcTableReadable<TOcTable extends OcTable> {
    TOcTable readOcTable() throws IOException, ClassNotFoundException;
}
