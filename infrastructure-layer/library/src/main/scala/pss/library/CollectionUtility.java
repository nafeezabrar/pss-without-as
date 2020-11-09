package pss.library;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtility {
    public static <T> boolean hasDuplicate(Collection<T> collection) {
        Set<T> set = new HashSet<>(collection);
        return set.size() != collection.size();
    }
}
