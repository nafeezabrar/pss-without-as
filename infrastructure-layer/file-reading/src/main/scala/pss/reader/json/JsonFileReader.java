package pss.reader.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pss.exception.ReaderException;
import pss.library.FileUtility;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JsonFileReader {
    protected final String fileName;
    protected JSONObject jsonObject;

    public JsonFileReader(String fileName) throws ReaderException {
        this.fileName = fileName;
        init();
    }

    private void init() throws ReaderException {
        JSONParser jsonParser = new JSONParser();
        FileUtility fileUtility = new FileUtility();
        try {
            File file = fileUtility.getFilePath(fileName);
            FileReader fileReader = new FileReader(file);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
        } catch (Exception e) {
            throw new ReaderException("Could Not Read File", e);
        }
    }

    public Object getObject(String key) throws ReaderException {
        Object object = jsonObject.get(key);
        if (object == null)
            throw new ReaderException(String.format("Key %s Not Found", key));
        return object;
    }

    public JSONObject getJsonObject(String key) throws ReaderException {
        Object object = getObject(key);
        if (object.getClass() != JSONObject.class)
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        return (JSONObject) object;
    }

    public JSONObject getJsonObject(JSONObject parentObject, String key) throws ReaderException {
        Object childObject = parentObject.get(key);
        if (childObject.getClass() != JSONObject.class) {
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        }
        return (JSONObject) childObject;
    }

    public Set<JSONObject> getJsonObjects(String key) throws ReaderException {
        Object object = jsonObject.get(key);
        if (object.getClass() != JSONArray.class) {
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        }
        JSONArray jsonArray = (JSONArray) object;
        Set<JSONObject> jsonObjects = new HashSet<>();
        jsonObjects.addAll(jsonArray);
        return jsonObjects;
    }

    public Set<JSONObject> getJsonObjects(JSONObject parentObject, String key) throws ReaderException {
        Object object = parentObject.get(key);
        if (object.getClass() != JSONArray.class) {
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        }
        JSONArray jsonArray = (JSONArray) object;
        Set<JSONObject> jsonObjects = new HashSet<>();
        jsonObjects.addAll(jsonArray);
        return jsonObjects;
    }

    public String getString(JSONObject jsonObject, String key) throws ReaderException {
        Object object = jsonObject.get(key);
        if (object.getClass() != String.class)
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        return (String) object;
    }

    public int getInt(JSONObject jsonObject, String key) throws ReaderException {
        long value = getLong(jsonObject, key);
        return (int) value;
    }

    public long getLong(JSONObject jsonObject, String key) throws ReaderException {
        Object object = jsonObject.get(key);
        if (object.getClass() != Long.class)
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        return (long) object;
    }

    public boolean containsKey(String key) {
        return jsonObject.keySet().contains(key);
    }

    public boolean containsKey(JSONObject childJsonObject, String key) {
        return childJsonObject.keySet().contains(key);
    }

    public String getString(String key) throws ReaderException {
        Object object = getObject(key);
        if (object.getClass() != String.class)
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        return (String) object;
    }

    public String[] getStringArray(String key) throws ReaderException {
        Object object = jsonObject.get(key);
        if (object.getClass() != JSONArray.class) {
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        }
        JSONArray jsonArray = (JSONArray) object;
        int size = jsonArray.size();
        String[] strings = new String[size];
        Iterator iterator = jsonArray.iterator();
        for (int i = 0; i < size; i++) {
            Object item = iterator.next();
            if (item.getClass() != String.class)
                throw new ReaderException(String.format("Key %s Type Not Matched", key));
            strings[i] = (String) item;
        }
        return strings;
    }

    public String[] getStringArray(JSONObject childJsonObject, String key) throws ReaderException {
        Object object = childJsonObject.get(key);
        if (object.getClass() != JSONArray.class) {
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        }
        JSONArray jsonArray = (JSONArray) object;
        int size = jsonArray.size();
        String[] strings = new String[size];
        Iterator iterator = jsonArray.iterator();
        for (int i = 0; i < size; i++) {
            Object item = iterator.next();
            if (item.getClass() != String.class)
                throw new ReaderException(String.format("Key %s Type Not Matched", key));
            strings[i] = (String) item;
        }
        return strings;
    }

    public Set<String> getStringSet(String key) throws ReaderException {
        return new HashSet<>(Arrays.asList(getStringArray(key)));
    }

    public int[] getIntArray(String key) throws ReaderException {
        Object object = jsonObject.get(key);
        if (object.getClass() != JSONArray.class) {
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        }
        JSONArray jsonArray = (JSONArray) object;
        int size = jsonArray.size();
        int[] intSet = new int[size];
        Iterator iterator = jsonArray.iterator();
        for (int i = 0; i < size; i++) {
            Object item = iterator.next();
            if (item.getClass() != Long.class)
                throw new ReaderException(String.format("Key %s Type Not Matched", key));
            long value = (long) item;
            intSet[i] = (int) value;
        }
        return intSet;
    }

    public int[] getIntArray(JSONObject childJsonObject, String key) throws ReaderException {
        Object object = childJsonObject.get(key);
        if (object.getClass() != JSONArray.class) {
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        }
        JSONArray jsonArray = (JSONArray) object;
        int size = jsonArray.size();
        int[] intSet = new int[size];
        Iterator iterator = jsonArray.iterator();
        for (int i = 0; i < size; i++) {
            Object item = iterator.next();
            if (item.getClass() != Long.class)
                throw new ReaderException(String.format("Key %s Type Not Matched", key));
            long value = (long) item;
            intSet[i] = (int) value;
        }
        return intSet;
    }

    public long getLong(String key) throws ReaderException {
        Object object = getObject(key);
        if (object.getClass() != Long.class)
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        return (long) object;
    }

    public int getInt(String key) throws ReaderException {
        long value = getLong(key);
        return (int) value;
    }

    public double getDouble(String key) throws ReaderException {
        Object object = getObject(key);
        if (object.getClass() != Double.class)
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        return (double) object;
    }

    public double getDouble(JSONObject jsonObject, String key) throws ReaderException {
        Object object = jsonObject.get(key);
        if (object.getClass() != Double.class)
            throw new ReaderException(String.format("Key %s Type Not Matched", key));
        return (double) object;
    }
}
