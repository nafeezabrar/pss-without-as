package pss.variable.generation;

import pss.data.ooi.Ooi;

import java.util.HashSet;
import java.util.Set;

public class CapitalLetterOoiGenerator implements OoiGenerable {
    @Override
    public Set<Ooi> generateOoi(int n, int startOoiId) {
        Set<Ooi> oois = new HashSet<>();

        for (int i = 0; i < n; i++) {
            int ooiId = i + 1;
            Ooi ooi = new Ooi(ooiId, String.valueOf((char) (i + 'A')));
            oois.add(ooi);

        }
        return oois;
    }
}
