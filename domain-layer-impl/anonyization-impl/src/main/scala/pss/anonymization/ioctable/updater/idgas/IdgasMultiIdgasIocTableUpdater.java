package pss.anonymization.ioctable.updater.idgas;

import pss.anonymization.IdgasIocTableUpdatable;
import pss.data.anonymity.MultiAnonymity;
import pss.data.dimension.Dimension;
import pss.data.ioc_table.IocCell;
import pss.data.ioc_table.MultiIocRow;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;

import java.util.*;

public class IdgasMultiIdgasIocTableUpdater implements IdgasIocTableUpdatable<MultiAnonymizedLocalOoiSet, MultiLocalOoiCombination, MultiAnonymity> {
    protected final MultiOcTable iocTable;

    public IdgasMultiIdgasIocTableUpdater(MultiOcTable iocTable) {
        this.iocTable = iocTable;
    }


    @Override
    public MultiAnonymizedLocalOoiSet getAnonymizedOois(MultiLocalOoiCombination localOoiCombination, MultiAnonymity anonymity) {
        Map<MultiLocalOoiCombination, MultiIocRow> iocMap = iocTable.getIocMap();
        MultiIocRow iocRow = iocMap.get(localOoiCombination);
        Set<Dimension> dimensions = localOoiCombination.getLocalOoiMap().keySet();
        Map<Dimension, Integer> ooiIdMap = localOoiCombination.getLocalOoiMap();
        Map<Dimension, Integer> anonymityMap = anonymity.getAnonymityMap();
        Map<Dimension, Set<Integer>> anonymizedOoiMap = new HashMap<>();
        Map<Dimension, Set<IocCell>> iocCellMap = iocRow.getIocCellMap();
        for (Dimension dimension : dimensions) {
            Set<IocCell> iocCells = iocCellMap.get(dimension);
            List<IocCell> iocCellList = new ArrayList<>(iocCells);
            Collections.sort(iocCellList, new Comparator<IocCell>() {
                @Override
                public int compare(IocCell o1, IocCell o2) {
                    return Integer.compare(o2.getIocCount(), o1.getIocCount());
                }
            });
            Set<Integer> anonymizedOoiSet = new HashSet<>();
            anonymizedOoiSet.add(ooiIdMap.get(dimension));
            for (int i = 0; i < anonymityMap.get(dimension) - 1; i++) {
                anonymizedOoiSet.add(iocCellList.get(i).getOoiId());
            }
            anonymizedOoiMap.put(dimension, anonymizedOoiSet);
        }
        return new MultiAnonymizedLocalOoiSet(anonymizedOoiMap);
    }

    @Override
    public void update(MultiLocalOoiCombination localOoiCombination, MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet) {
        Map<MultiLocalOoiCombination, MultiIocRow> iocMap = iocTable.getIocMap();
        MultiIocRow iocRow = iocMap.get(localOoiCombination);
        iocRow.incrementCount(anonymizedLocalOoiSet);
    }
}
