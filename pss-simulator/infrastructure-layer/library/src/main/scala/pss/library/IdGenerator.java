package pss.library;

import java.util.Map;
import java.util.Set;

public interface IdGenerator<T> {
    Map<T, Integer> generateUniqueIdMap(Set<T> set);
}
