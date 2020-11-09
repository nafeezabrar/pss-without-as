package pss.library;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MyRandomTest {

    private MyRandom myRandom;
    private int minBound = 3;
    private int maxBound = 10;
    private int fixedLength = 7;

    @Before
    public void setup() {
        myRandom = new MyRandom();
    }

    @Test
    public void testBoundNextNameReturnsStringWithinBounds() throws Exception {
        String name = myRandom.nextName(minBound, maxBound);
        int nameLength = name.length();
        assertTrue(nameLength >= minBound);
        assertTrue(nameLength < maxBound);
    }

    @Test
    public void testBoundNextNameReturnsRandomNames() throws Exception {
        String name1 = myRandom.nextName(minBound, maxBound);
        String name2 = myRandom.nextName(minBound, maxBound);
        assertNotEquals(name1, name2);
    }

    @Test
    public void testFixedLengthNextNameReturnsStringWithinBounds() throws Exception {
        String name = myRandom.nextName(fixedLength);
        int nameLength = name.length();
        assertEquals(fixedLength, nameLength);
    }

    @Test
    public void testFixedLengthNextNameReturnsRandomNames() throws Exception {
        String name1 = myRandom.nextName(fixedLength);
        String name2 = myRandom.nextName(fixedLength);
        assertNotEquals(name1, name2);
    }

    @Test
    public void boundNextIntReturnsBetweenBound() throws Exception {
        int randomInt = myRandom.nextInt(minBound, maxBound);

        assertTrue(randomInt >= minBound);
        assertTrue(randomInt < maxBound);
    }

    @Test
    public void nextAlphabetReturnValidAlphabet() throws Exception {
        char alphabet = myRandom.nextAlphabet();
        assertTrue(isValidAlphabet(alphabet));
    }

    @Test
    public void nextAlphabetUpperReturnValidUpperCaseAlphabet() throws Exception {
        char alphabet = myRandom.nextAlphabet(true);
        assertTrue(isValidUpperCaseAlphabet(alphabet));
    }

    @Test
    public void nextAlphabetLowerReturnValidLowerCaseAlphabet() throws Exception {
        char alphabet = myRandom.nextAlphabet(false);
        assertTrue(isValidLowerCaseAlphabet(alphabet));
    }

    private boolean isValidAlphabet(char alphabet) {
        boolean isUpperCase = isValidUpperCaseAlphabet(alphabet);
        boolean isLowerCase = isValidLowerCaseAlphabet(alphabet);

        return isUpperCase || isLowerCase;
    }

    private boolean isValidUpperCaseAlphabet(char alphabet) {
        return 'A' <= alphabet && alphabet <= 'Z';
    }


    private boolean isValidLowerCaseAlphabet(char alphabet) {
        return 'a' <= alphabet && alphabet <= 'z';
    }

    @Test
    public void testNextObjectReturnsRandomObjects() throws Exception {
        Set<String> strings = getStringSet(10);
        Set<String> randomStrings = new HashSet<>();

        randomStrings.add(myRandom.nextItem(strings));
        randomStrings.add(myRandom.nextItem(strings));
        randomStrings.add(myRandom.nextItem(strings));
        randomStrings.add(myRandom.nextItem(strings));
        randomStrings.add(myRandom.nextItem(strings));

        assertNotEquals(1, randomStrings.size());
    }

    private Set<String> getStringSet(int setSize) {
        Set<String> strings = new HashSet<>();
        for (int i = 0; i < setSize; i++) {
            strings.add(String.format("String %d", i));
        }
        return strings;
    }

    @Test
    public void testNextItems() throws Exception {
        Set<String> stringSet = getStringSet(6);
        int itemCount = 3;
        Set<String> nextItems = myRandom.nextItems(itemCount, stringSet);
        assertNotNull(nextItems);
        assertEquals(itemCount, nextItems.size());
        for (String str : nextItems) {
            assertTrue(stringSet.contains(str));
        }
    }
}