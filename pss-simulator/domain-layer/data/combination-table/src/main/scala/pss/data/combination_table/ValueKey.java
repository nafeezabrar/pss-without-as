package pss.data.combination_table;

import java.util.Objects;

public class ValueKey {
    protected final int id;

    public ValueKey(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueKey valueKey = (ValueKey) o;
        return id == valueKey.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

