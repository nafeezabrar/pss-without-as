package pss.data.valuemap;

import java.io.Serializable;

public class Value implements Serializable {
    private static long serialVersionUID = 1L;
    protected final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getIntValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value1 = (Value) o;

        return value == value1.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
