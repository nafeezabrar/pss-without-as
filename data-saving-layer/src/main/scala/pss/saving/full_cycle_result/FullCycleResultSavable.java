package pss.saving.full_cycle_result;


import pss.result.full_cycle.FullCycleResult;

import java.util.List;

public interface FullCycleResultSavable {
    void saveFullCycleResult(List<FullCycleResult> fullCycleResults);
}
