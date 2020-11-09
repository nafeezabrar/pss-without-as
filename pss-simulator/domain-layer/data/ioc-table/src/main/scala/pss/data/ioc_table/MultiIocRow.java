package pss.data.ioc_table;

import pss.data.dimension.Dimension;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiIocRow extends IocRow<MultiAnonymizedLocalOoiSet> {
    protected final Map<Dimension, Set<IocCell>> iocCellMap;

    public MultiIocRow(Map<Dimension, Set<IocCell>> iocCellMap) {
        this.iocCellMap = iocCellMap;
    }

    @Override
    public void incrementCount(MultiAnonymizedLocalOoiSet multiAnonymizedLocalOoiSet) {
        Map<Dimension, Set<Integer>> anonymizedOoiIds = multiAnonymizedLocalOoiSet.getAnonymizedOoiSets();
        Set<Dimension> dimensions = iocCellMap.keySet();
        for (Dimension dimension : dimensions) {
            for (IocCell iocCell : iocCellMap.get(dimension)) {
                if (!anonymizedOoiIds.get(dimension).contains(iocCell.ooiId))
                    iocCell.iocCount++;
            }
        }
    }

    @Override
    MultiIocRow cloneIocRow() {
        Map<Dimension, Set<IocCell>> clonedIocCellMap = new HashMap<>();
        for (Dimension dimension : iocCellMap.keySet()) {
            Set<IocCell> clonedIocCells = new HashSet<>();
            for (IocCell iocCell : iocCellMap.get(dimension)) {
                clonedIocCells.add(iocCell.cloneIocCell());
            }
            clonedIocCellMap.put(dimension, clonedIocCells);
        }
        return new MultiIocRow(clonedIocCellMap);
    }

    public Map<Dimension, Set<IocCell>> getIocCellMap() {
        return iocCellMap;
    }
}
