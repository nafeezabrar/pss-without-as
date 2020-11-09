package pss.reader.config;

import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

public abstract class JsonConfigReader {
    protected final String fileName;
    protected JsonFileReader jsonFileReader;

    protected JsonConfigReader(String fileName) throws ReaderException {
        this.fileName = fileName;
        jsonFileReader = new JsonFileReader(fileName);
    }
}
