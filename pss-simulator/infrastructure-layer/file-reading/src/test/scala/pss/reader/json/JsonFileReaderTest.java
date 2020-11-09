package pss.reader.json;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import pss.exception.ReaderException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonFileReaderTest {
    private final String testJsonFileName = "testing-files/json-test-file.json";
    private final String validKey = "nafeez";
    private final String validValue = "Nafeez Abrar";
    private final String invalidKey = "shaolin";
    private final String stringArrayKey = "stringArrayKey";
    private final String[] expectedStrings = {"a", "b", "c"};
    private final String stringKey = "stringType";
    private final String intKey = "intType";
    private final int expectedInt = 1;
    private final String expectedNestedValue = "child";
    private final String nestedObjectKey = "nestedObject";
    private final String levelOneKey = "levelOne";
    private final String levelTwo = "levelTwo";
    private JsonFileReader jsonFileReader;

    @Before
    public void setUp() throws Exception {
        jsonFileReader = new JsonFileReader(testJsonFileName);
    }

    @Test
    public void testValidKey() throws Exception {
        assertEquals(true, jsonFileReader.containsKey(validKey));
        String value = (String) jsonFileReader.getObject(validKey);
        assertEquals(validValue, value);
    }

    @Test
    public void testInvalidKey() throws Exception {
        assertEquals(false, jsonFileReader.containsKey(invalidKey));
        try {
            jsonFileReader.getObject(invalidKey);
            fail("missing exception");
        } catch (Exception e) {
            assertEquals(ReaderException.class, e.getClass());
        }
    }

    @Test
    public void testJsonStringArray() throws Exception {
        String[] stringArray = jsonFileReader.getStringArray(stringArrayKey);
        Set<String> stringSet = new HashSet<>(Arrays.asList(stringArray));
        Set<String> expectedStringSet = new HashSet<>(Arrays.asList(expectedStrings));
        assertEquals(expectedStringSet, stringSet);
    }

    @Test
    public void testInvalidValueType() throws Exception {
        try {
            jsonFileReader.getString(stringKey);
            fail("missing exception");
        } catch (Exception e) {
            assertEquals(ReaderException.class, e.getClass());
        }
    }

    @Test
    public void testValidValueType() throws Exception {
        int value = jsonFileReader.getInt(intKey);
        assertEquals(expectedInt, value);
    }

    @Test
    public void testNestedObject() throws Exception {
        JSONObject nestedObject = jsonFileReader.getJsonObject(nestedObjectKey);
        JSONObject levelOneObject = jsonFileReader.getJsonObject(nestedObject, levelOneKey);
        String nestedValue = jsonFileReader.getString(levelOneObject, levelTwo);
        assertEquals(expectedNestedValue, nestedValue);

    }
}