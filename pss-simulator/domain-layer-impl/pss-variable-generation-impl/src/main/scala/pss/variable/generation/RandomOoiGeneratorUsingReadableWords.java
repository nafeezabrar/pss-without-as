package pss.variable.generation;

import com.github.javafaker.Faker;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.valuemap.Value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RandomOoiGeneratorUsingReadableWords implements OoiGenerable {
    private final Faker faker;

    public RandomOoiGeneratorUsingReadableWords(Faker faker) {
        this.faker = faker;
    }

    @Override
    public Set<Ooi> generateOoi(int n, int startOoiId) {
        Set<Ooi> oois = new HashSet<>();
        Map<SingleOoiCombination, Value> ooiValues = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int ooiId = i + 1;
            Ooi ooi = new Ooi(ooiId, faker.commerce().material());
            oois.add(ooi);
            SingleOoiCombination ooiCombination = new SingleOoiCombination(ooi);
            ooiValues.put(ooiCombination, new Value(i));
        }
        return oois;
    }
}
