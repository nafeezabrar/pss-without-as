package pss.saving.octable;

import pss.config.printing.DefaultPrintingFileName;
import pss.data.oc_table.OcTable;
import pss.library.SerializableObjectSaver;
import pss.saving.OcTableSavable.OcTableSavable;

import java.io.IOException;

public class TextFileOcTableSaver<T extends OcTable> implements OcTableSavable<T> {
    protected final String fileName;
    protected final String resultDirectory;

    public TextFileOcTableSaver(String fileName, String resultDirectory) {
        this.fileName = fileName;
        this.resultDirectory = resultDirectory;
    }

    @Override
    public void saveOcTable(T ocTable) throws IOException, ClassNotFoundException {
        String resultFileName = DefaultPrintingFileName.getOcTableSaveFileName(resultDirectory);
        SerializableObjectSaver<T> ocTableSaver = new SerializableObjectSaver<>(resultFileName);
        ocTableSaver.saveObject(ocTable);
    }
}
