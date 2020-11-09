package pss.library;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class SetUtilityTest {
    @Test
    public void testTwoSetUniqueTest() throws Exception {
        String one = "one";
        String two = "two";
        String three = "three";
        String four = "four";
        Set<String> firstSet = new HashSet<>();
        firstSet.add(one);
        firstSet.add(two);
        Set<String> secondSet = new HashSet<>();
        secondSet.add(three);
        secondSet.add(four);
        assertTrue(SetUtility.checkTwoSetUnique(firstSet, secondSet));
        firstSet.add(four);
        assertFalse(SetUtility.checkTwoSetUnique(firstSet, secondSet));
    }

    @Test
    public void intersectTwoSetCorrectly() throws Exception {
        String one = "one";
        String two = "two";
        String three = "three";
        String four = "four";

        Set<String> firstSet = new HashSet<>();
        firstSet.add(one);
        firstSet.add(two);
        firstSet.add(four);

        Set<String> secondSet = new HashSet<>();
        secondSet.add(three);
        secondSet.add(four);

        Set<String> expected = new HashSet<>();
        expected.add(four);

        Set<String> actual = SetUtility.intersect(firstSet, secondSet);

        assertEquals(expected, actual);
    }
}