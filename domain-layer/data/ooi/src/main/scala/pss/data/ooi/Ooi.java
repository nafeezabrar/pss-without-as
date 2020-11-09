package pss.data.ooi;

import java.util.Objects;

public class Ooi {
    protected final int id;
    protected final String name;

    public Ooi(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((Ooi) that);
    }

    private boolean equals(Ooi that) {
        return this.id == that.id && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return 34671 ^ id ^ Objects.hashCode(name);
    }
}
