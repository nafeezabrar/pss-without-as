package pss.library;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SequentialIdGeneratorTest {
    private SequentialIdGenerator<String> idGenerator;
    private Set<String> strings;
    private String one = "ONE";
    private String two = "TWO";
    private String three = "THREE";

    @Before
    public void setUp() throws Exception {
        idGenerator = new SequentialIdGenerator<>();
        strings = new HashSet<>();
        strings.add(one);
        strings.add(two);
        strings.add(three);
    }

    @Test
    public void testGeneratedMapHasValidLength() throws Exception {
        Map<String, Integer> idMap = idGenerator.generateUniqueIdMap(strings);
        assertEquals(3, idMap.size());
    }

    @Test
    public void testGeneratedMapHasUniqueId() throws Exception {
        Map<String, Integer> idMap = idGenerator.generateUniqueIdMap(strings);
        Set<Integer> uniqueIdSet = new HashSet<>();
        for (int id : idMap.values())
            uniqueIdSet.add(id);
        assertEquals(3, uniqueIdSet.size());
    }

    @Test
    public void testGeneratedMapContainsEachElement() throws Exception {
        Map<String, Integer> idMap = idGenerator.generateUniqueIdMap(strings);
        assertTrue(idMap.containsKey(one));
        assertTrue(idMap.containsKey(two));
        assertTrue(idMap.containsKey(three));
    }
}