package pss.variable.generation;

import pss.data.ooi.Ooi;

import java.util.Set;

public interface OoiGenerable {
    Set<Ooi> generateOoi(int n, int startOoiId);
}
