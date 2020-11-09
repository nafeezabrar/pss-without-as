package pss.library.combinatorics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinatoricsUtility {
    public static Set<Set<Integer>> getCombinations(int[] input, int size) {
        HashSet<Integer> inputSet = new HashSet<>();
        for (int i = 0; i < input.length; i++) {
            inputSet.add(i);
        }
        return getCombinations(inputSet, size);
    }

    public static Set<Set<Integer>> getCombinations(Set<Integer> input, int size) {
        if (size == 0) {
            return new HashSet<>();
        }

        if (size == 1) {
            Set<Set<Integer>> combinations = new HashSet<>();
            for (Integer integer : input) {
                Set<Integer> singleCombination = new HashSet<>();
                singleCombination.add(integer);
                combinations.add(singleCombination);
            }
            return combinations;
        }

        if (input.isEmpty()) return new HashSet<>();

        Integer first = input.iterator().next();

        Set<Integer> withoutFirstInput = new HashSet<>(input);
        withoutFirstInput.remove(first);

        Set<Set<Integer>> withOutFirstCombinations = getCombinations(withoutFirstInput, size);

        Set<Set<Integer>> withFirstCombinations = getCombinations(withoutFirstInput, size - 1);
        for (Set<Integer> withFirstCombination : withFirstCombinations) {
            withFirstCombination.add(first);
        }

        Set<Set<Integer>> totalCombinations = new HashSet<>();
        totalCombinations.addAll(withOutFirstCombinations);
        totalCombinations.addAll(withFirstCombinations);

        return totalCombinations;
    }

    public static Set<List<Integer>> getPermutations(int[] input) {
        if (input.length == 0) return new HashSet<>();
        return getPermutations(input, 0, new ArrayList<>());
    }

    private static Set<List<Integer>> getPermutations(int[] input, int currentIndex, ArrayList<Integer> currentPermutation) {
        Set<List<Integer>> permutations = new HashSet<>();
        if (currentIndex == input.length) {
            permutations.add(currentPermutation);
        } else {
            for (int i = 0; i < input.length; i++) {
                if (contains(input[i], currentPermutation)) {
                    continue;
                }
                ArrayList<Integer> newPermutation = cloneList(currentPermutation);
                newPermutation.add(input[i]);
                Set<List<Integer>> morePermutations = getPermutations(input, currentIndex + 1, newPermutation);
                permutations.addAll(morePermutations);
            }

        }
        return permutations;
    }

    private static boolean contains(int target, ArrayList<Integer> input) {
        for (Integer element : input) {
            if (element == target) return true;
        }
        return false;
    }

    public static ArrayList<Integer> cloneList(ArrayList<Integer> list) {
        ArrayList<Integer> clonedList = new ArrayList<>();
        clonedList.addAll(list);
        return clonedList;
    }
}
