package pss.library;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CollectionUtilityTest {

    private Collection<Integer> collection;

    @Before
    public void initList() {
        collection = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            collection.add(i);
        }
    }

    @Test
    public void canCheckDuplicate() throws Exception {
        assertEquals(CollectionUtility.hasDuplicate(collection), false);
        collection.add(2);
        assertEquals(CollectionUtility.hasDuplicate(collection), true);
    }
}