package pss.library.combinatorics;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CombinatoricsUtilityTest {

    private Set<Integer> input;

    @Before
    public void setUp() throws Exception {
        input = new HashSet<>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(5);
    }

    @Test
    public void generateCombinationsCorrectly_forZeroSize() throws Exception {

        Set<Set<Integer>> expectedCombinations = new HashSet<>();

        Set<Set<Integer>> actualCombinations = CombinatoricsUtility.getCombinations(input, 0);
        assertEquals(expectedCombinations, actualCombinations);
    }

    @Test
    public void generateCombinationsCorrectly_forOneSize() throws Exception {

        Set<Set<Integer>> expectedCombinations = new HashSet<>();
        Set<Integer> one = new HashSet<>();
        one.add(1);
        expectedCombinations.add(one);
        Set<Integer> two = new HashSet<>();
        two.add(2);
        expectedCombinations.add(two);
        Set<Integer> three = new HashSet<>();
        three.add(3);
        expectedCombinations.add(three);
        Set<Integer> four = new HashSet<>();
        four.add(4);
        expectedCombinations.add(four);
        Set<Integer> five = new HashSet<>();
        five.add(5);
        expectedCombinations.add(five);

        Set<Set<Integer>> actualCombinations = CombinatoricsUtility.getCombinations(input, 1);
        assertEquals(expectedCombinations, actualCombinations);
    }

    @Test
    public void generateCombinationsCorrectly_forThreeSize() throws Exception {
        Set<Set<Integer>> expectedCombinations = new HashSet<>();
        Set<Integer> oneTwoThree = new HashSet<>();
        oneTwoThree.add(1);
        oneTwoThree.add(2);
        oneTwoThree.add(3);
        expectedCombinations.add(oneTwoThree);
        Set<Integer> oneTwoFour = new HashSet<>();
        oneTwoFour.add(1);
        oneTwoFour.add(2);
        oneTwoFour.add(4);
        expectedCombinations.add(oneTwoFour);
        Set<Integer> oneTwoFive = new HashSet<>();
        oneTwoFive.add(1);
        oneTwoFive.add(2);
        oneTwoFive.add(5);
        expectedCombinations.add(oneTwoFive);
        Set<Integer> oneThreeFour = new HashSet<>();
        oneThreeFour.add(1);
        oneThreeFour.add(3);
        oneThreeFour.add(4);
        expectedCombinations.add(oneThreeFour);
        Set<Integer> oneThreeFive = new HashSet<>();
        oneThreeFive.add(1);
        oneThreeFive.add(3);
        oneThreeFive.add(5);
        expectedCombinations.add(oneThreeFive);
        Set<Integer> oneFourFive = new HashSet<>();
        oneFourFive.add(1);
        oneFourFive.add(4);
        oneFourFive.add(5);
        expectedCombinations.add(oneFourFive);
        Set<Integer> twoThreeFour = new HashSet<>();
        twoThreeFour.add(2);
        twoThreeFour.add(3);
        twoThreeFour.add(4);
        expectedCombinations.add(twoThreeFour);
        Set<Integer> twoThreeFive = new HashSet<>();
        twoThreeFive.add(2);
        twoThreeFive.add(3);
        twoThreeFive.add(5);
        expectedCombinations.add(twoThreeFive);
        Set<Integer> twoFourFive = new HashSet<>();
        twoFourFive.add(2);
        twoFourFive.add(4);
        twoFourFive.add(5);
        expectedCombinations.add(twoFourFive);
        Set<Integer> threeFourFive = new HashSet<>();
        threeFourFive.add(3);
        threeFourFive.add(4);
        threeFourFive.add(5);
        expectedCombinations.add(threeFourFive);


        Set<Set<Integer>> actualCombinations = CombinatoricsUtility.getCombinations(input, 3);
        assertEquals(expectedCombinations, actualCombinations);
    }

    @Test
    public void generatePermutationsForEmptyList() throws Exception {
        int[] input = new int[0];
        Set<List<Integer>> permutations = CombinatoricsUtility.getPermutations(input);
        assertTrue(permutations.isEmpty());

    }

    @Test
    public void generatePermutationsForOneSizedList() throws Exception {
        int[] input = new int[]{1};
        Set<List<Integer>> permutations = CombinatoricsUtility.getPermutations(input);
        assertEquals(permutations.size(), 1);
        List<Integer> permutation = permutations.iterator().next();
        assertEquals(permutation.size(), 1);
        assertTrue(permutation.get(0) == 1);
    }

    @Test
    public void generatePermutationsForTwoSizedList() throws Exception {
        int[] input = new int[]{1, 2};
        Set<List<Integer>> permutations = CombinatoricsUtility.getPermutations(input);
        assertEquals(permutations.size(), 2);
    }

    @Test
    public void generatePermutationsForThreeSizedList() throws Exception {
        int[] input = new int[]{1, 2, 3};
        Set<List<Integer>> permutations = CombinatoricsUtility.getPermutations(input);
        assertEquals(permutations.size(), 6);
    }
}