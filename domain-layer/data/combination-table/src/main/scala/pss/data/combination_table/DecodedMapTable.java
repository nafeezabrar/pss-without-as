package pss.data.combination_table;

import pss.data.ooi.local.combination.LocalOoiCombination;

import java.util.HashSet;
import java.util.Set;

public class DecodedMapTable<TLocalOoiCombination extends LocalOoiCombination> {
    protected Set<DecodedState<TLocalOoiCombination>> decodedStates;

    public DecodedMapTable(Set<DecodedState<TLocalOoiCombination>> decodedStates) {
        this.decodedStates = decodedStates;
    }

    public Set<DecodedState<TLocalOoiCombination>> getDecodedStates() {
        return decodedStates;
    }

    public void addValueCombination(DecodedState<TLocalOoiCombination> row) {
        decodedStates.add(row);
    }

    public void removeValueCombination(DecodedState decodedState) {
        decodedStates.remove(decodedState);
    }

    public void removeDecodedStates(Set<DecodedState> decodedStates) {
        for (DecodedState decodedState : decodedStates) {
            removeValueCombination(decodedState);
        }
    }

    public int getTotalCombinations() {
        return decodedStates.size();
    }

    public Set<DecodedState> getAlignedDecodedStates(NotAllowedMappings notAllowedMappings) {
        Set<DecodedState> alignedDecodedStates = new HashSet<>();
        for (DecodedState decodedState : decodedStates) {
            if (decodedState.isSatisfied(notAllowedMappings)) {
                alignedDecodedStates.add(decodedState);
            }
        }
        return alignedDecodedStates;
    }
}
