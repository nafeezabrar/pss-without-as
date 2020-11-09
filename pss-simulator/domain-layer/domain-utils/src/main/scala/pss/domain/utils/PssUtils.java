package pss.domain.utils;

import pss.data.mapper.ooi_id.LocalOoiMapper;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.pssvariable.PssVariables;

public interface PssUtils<TPssVariables extends PssVariables, TLocalOoiCombination extends LocalOoiCombination, TLocalOoiMapper extends LocalOoiMapper> {
    int countTotalOois(TPssVariables pssVariables);

    SingleOoiCombination getOoiCombination(TLocalOoiCombination localOoiCombination, TLocalOoiMapper localOoiMapper);
}
