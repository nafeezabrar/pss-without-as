package pss.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SequentialIdGenerator<T> implements IdGenerator<T> {
    public Map<T, Integer> generateUniqueIdMap(Set<T> set) {
        Map<T, Integer> idMap = new HashMap<>();
        int id = 1;
        for (T t : set) {
            idMap.put(t, id);
            id++;
        }
        return idMap;
    }
}
