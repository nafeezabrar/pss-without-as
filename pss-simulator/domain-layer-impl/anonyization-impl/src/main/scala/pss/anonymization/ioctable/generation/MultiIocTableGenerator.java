package pss.anonymization.ioctable.generation;

import pss.data.dimension.Dimension;
import pss.data.ioc_table.IocCell;
import pss.data.ioc_table.MultiIocRow;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.domain.utils.ooi_local.MultiLocalOoiCombinationMaker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiIocTableGenerator implements IocTableGenerator<MultiOcTable, MultiLocalOoiCollection> {
    private Set<MultiLocalOoiCombination> ooiIdCombinations;
    private MultiLocalOoiCollection ooiIdCollection;
    private Dimension[] dimensions;
    private int totalDimensions;

    @Override
    public MultiOcTable generateIocTable(MultiLocalOoiCollection ooiIdCollection) {
        this.ooiIdCollection = ooiIdCollection;
        Set<Dimension> dimensionSet = ooiIdCollection.getLocalOoiMap().keySet();
        totalDimensions = dimensionSet.size();
        dimensions = dimensionSet.toArray(new Dimension[totalDimensions]);
        MultiLocalOoiCombinationMaker ooiIdUtil = new MultiLocalOoiCombinationMaker(ooiIdCollection);
        ooiIdCombinations = ooiIdUtil.generateOoiCombinations();
        Map<MultiLocalOoiCombination, MultiIocRow> iocRowMap = new HashMap<>();
        for (MultiLocalOoiCombination localOoiCombination : ooiIdCombinations) {
            MultiIocRow iocRow = createMultiIocRow(localOoiCombination);
            iocRowMap.put(localOoiCombination, iocRow);
        }
        return new MultiOcTable(ooiIdCombinations.size(), iocRowMap);
    }

    private MultiIocRow createMultiIocRow(MultiLocalOoiCombination localOoiCombination) {
        Map<Dimension, Set<IocCell>> iocCellMap = new HashMap<>();
        for (Dimension dimension : dimensions) {
            iocCellMap.put(dimension, new HashSet<>());
            int realOoiId = localOoiCombination.getLocalOoiMap().get(dimension);
            Set<IocCell> iocCells = createMultiIocCells(realOoiId, dimension);
            iocCellMap.put(dimension, iocCells);
        }
        return new MultiIocRow(iocCellMap);
    }

    private Set<IocCell> createMultiIocCells(int realOoiId, Dimension dimension) {
        Set<IocCell> iocCells = new HashSet<>();
        Set<Integer> ooiIdSet = ooiIdCollection.getLocalOoiMap().get(dimension);
        for (Integer ooiId : ooiIdSet) {
            if (ooiId != realOoiId) {
                iocCells.add(new IocCell(ooiId));
            }
        }
        return iocCells;
    }
}
