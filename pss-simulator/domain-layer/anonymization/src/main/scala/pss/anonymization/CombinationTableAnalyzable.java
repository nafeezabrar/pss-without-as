package pss.anonymization;

import pss.data.combination_table.DecodedState;
import pss.data.combination_table.NotAllowedMappings;
import pss.data.ooi.local.combination.LocalOoiCombination;

import java.util.Set;

public interface CombinationTableAnalyzable<TLocalOoiCombination extends LocalOoiCombination> {
    int getCombinationCountAlignedWith(NotAllowedMappings<TLocalOoiCombination> notAllowedMappings);

    Set<DecodedState<TLocalOoiCombination>> getCombinationsAlignedWith(NotAllowedMappings<TLocalOoiCombination> notAllowedMappings);
}
