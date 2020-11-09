package pss.library;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayUtilityTest {
    @Test
    public void testSuccessfulContains() throws Exception {
        assertTrue(ArrayUtility.containsInArray(new Integer[]{1, 2, 3}, 3));
    }

    @Test
    public void testUnsuccessfulContains() throws Exception {
        assertFalse(ArrayUtility.containsInArray(new Integer[]{1, 2, 3}, 4));
    }
}