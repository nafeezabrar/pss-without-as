package pss.anonymization.idgas;

import pss.anonymization.ioctable.updater.idgas.IdgasMultiIdgasIocTableUpdater;
import pss.data.dimension.Dimension;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.report.anonymizable.MultiIdgasAnonymizableReport;

import java.util.Map;
import java.util.Set;

public class MultiManualIdGasAnonymizer extends MultiIdgasAnonymizer {
    protected final Map<Integer, Map<Dimension, Set<Integer>>> anonymizedLocalOoiMap;

    public MultiManualIdGasAnonymizer(MultiOcTable iocTable, IdgasMultiIdgasIocTableUpdater iocTableUpdater, MultiLocalOoiCollection localOoiCollection, Map<Integer, Map<Dimension, Set<Integer>>> anonymizedLocalOoiMap) {
        super(iocTable, iocTableUpdater, localOoiCollection);
        this.anonymizedLocalOoiMap = anonymizedLocalOoiMap;
    }

    @Override
    protected MultiAnonymizedLocalOoiSet getMultiAnonymizedLocalOoiSet(MultiIdgasAnonymizableReport anonymizableReport, MultiLocalOoiCombination localOoiCombination) {
        int reportId = anonymizableReport.getId();
        Map<Dimension, Set<Integer>> anonymizedLocalOoiSet = anonymizedLocalOoiMap.get(reportId);
        return new MultiAnonymizedLocalOoiSet(anonymizedLocalOoiSet);
    }
}
