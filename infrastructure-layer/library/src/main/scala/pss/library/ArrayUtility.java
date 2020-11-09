package pss.library;

public class ArrayUtility {
    public static <T> boolean containsInArray(T[] array, T object) {
        for (T e : array) if (e.equals(object)) return true;
        return false;
    }
}
