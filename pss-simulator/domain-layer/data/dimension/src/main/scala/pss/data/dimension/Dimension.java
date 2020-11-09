package pss.data.dimension;

import java.io.Serializable;
import java.util.Objects;

public class Dimension implements Serializable {
    private static long serialVersionUID = 1L;
    protected final int id;
    protected final String name;
    protected final boolean isLocationDimension;

    public Dimension(int id, String name, boolean isLocationDimension) {
        this.id = id;
        this.name = name;
        this.isLocationDimension = isLocationDimension;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isLocationDimension() {
        return isLocationDimension;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || this.getClass() != that.getClass()) return false;

        return equals((Dimension) that);
    }

    private boolean equals(Dimension that) {
        return this.id == that.id && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return 36371 ^ id ^ Objects.hashCode(name);
    }
}
