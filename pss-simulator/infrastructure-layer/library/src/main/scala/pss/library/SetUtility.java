package pss.library;

import java.util.HashSet;
import java.util.Set;

public class SetUtility {
    public static <T> boolean checkTwoSetUnique(Set<T> firstSet, Set<T> secondSet) {
        return intersect(firstSet, secondSet).size() == 0;
    }

    public static <T> Set<T> intersect(Set<T> firstSet, Set<T> secondSet) {
        Set<T> intersection = new HashSet<>(firstSet);
        intersection.retainAll(secondSet);

        return intersection;
    }
}
