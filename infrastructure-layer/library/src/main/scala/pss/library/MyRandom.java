package pss.library;

import java.util.*;

public class MyRandom extends Random {

    public MyRandom() {
    }

    public MyRandom(long seed) {
        super(seed);
    }

    public String nextName(int minBound, int maxBound) {
        int randomLength = nextInt(minBound, maxBound);

        return nextName(randomLength);
    }

    public String nextName(int length) {
        StringBuilder nameBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++)
            nameBuilder.append(nextAlphabet());

        return nameBuilder.toString();
    }

    public int nextInt(int minBound, int maxBound) {
        return nextInt(maxBound - minBound) + minBound;
    }

    public char nextAlphabet() {
        boolean shouldUpper = nextBoolean();

        return nextAlphabet(shouldUpper);
    }

    public char nextAlphabet(boolean shouldUpper) {
        int randomIndex = nextInt(26);

        char base = shouldUpper ? 'A' : 'a';

        return (char) (base + randomIndex);
    }

    public <T> T nextItem(Collection<T> collection) {
        int target = nextInt(collection.size());
        int i = 0;

        for (T item : collection) {
            if (i == target) return item;
            i++;
        }

        throw new NoSuchElementException("Empty Collection");
    }

    public <T> Set<T> nextItems(int N, Collection<T> collection) {
        int count = 0;
        Set<T> items = new HashSet<>();
        while (count != N) {
            T item = nextItem(collection);
            if (!items.contains(item)) {
                items.add(item);
                count++;
            }

        }
        return items;
    }
}
