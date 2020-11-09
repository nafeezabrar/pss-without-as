package pss.saving.octable;

import pss.data.oc_table.OcTable;
import pss.library.SerializableObjectSaver;
import pss.saving.OcTableSavable.OcTableSavable;

import java.io.IOException;

public class TextFileOcTableSaverInMultipleFiles<T extends OcTable> implements OcTableSavable<T> {
    protected final String baseFileName;
    private int fileNameIndex;

    public TextFileOcTableSaverInMultipleFiles(String baseFileName, String resultDirectory) {
        this.baseFileName = baseFileName;
        this.fileNameIndex = 1;
    }

    @Override
    public void saveOcTable(T ocTable) throws IOException, ClassNotFoundException {
        String saveFileName = baseFileName.concat(fileNameIndex + "");
        fileNameIndex++;
        SerializableObjectSaver<T> ocTableSaver = new SerializableObjectSaver<>(saveFileName);
        ocTableSaver.saveObject(ocTable);
    }
}
