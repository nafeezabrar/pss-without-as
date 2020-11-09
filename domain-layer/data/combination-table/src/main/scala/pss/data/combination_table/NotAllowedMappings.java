package pss.data.combination_table;

import pss.data.ooi.local.combination.LocalOoiCombination;

import java.util.Map;
import java.util.Objects;

public class NotAllowedMappings<TLocalOoiCombination extends LocalOoiCombination> {
    private final Map<ValueKey, TLocalOoiCombination> constraints;

    public NotAllowedMappings(Map<ValueKey, TLocalOoiCombination> constraints) {
        this.constraints = constraints;
    }

    public Map<ValueKey, TLocalOoiCombination> getConstraints() {
        return constraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotAllowedMappings<?> that = (NotAllowedMappings<?>) o;
        return Objects.equals(constraints, that.constraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constraints);
    }
}
