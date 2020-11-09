package pss.reader.octable;

import pss.data.oc_table.OcTable;
import pss.library.SerializableObjectReader;

import java.io.IOException;

public class TextFileOcTableReaderWithIndex<TOcTable extends OcTable> implements OcTableReadable<TOcTable> {
    protected final String baseFileName;
    private int fileNameIndex;

    public TextFileOcTableReaderWithIndex(String baseFileName) {
        this.baseFileName = baseFileName;
        this.fileNameIndex = 1;
    }

    @Override
    public TOcTable readOcTable() throws IOException, ClassNotFoundException {
        String readerFileName = baseFileName.concat(fileNameIndex + "");
        fileNameIndex++;
        SerializableObjectReader<TOcTable> objectReader = new SerializableObjectReader<TOcTable>(readerFileName);

        return objectReader.getObject();
    }
}
