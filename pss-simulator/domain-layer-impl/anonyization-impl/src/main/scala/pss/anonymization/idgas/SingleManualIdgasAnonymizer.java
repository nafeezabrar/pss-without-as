package pss.anonymization.idgas;


import pss.anonymization.ioctable.updater.idgas.IdgasSingleIdgasIocTableUpdater;
import pss.data.ioc_table.SingleIocTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.anonymizable.SingleIdgasAnonymizableReport;

import java.util.Map;
import java.util.Set;

public class SingleManualIdgasAnonymizer extends SingleIdgasAnonymizer {

    protected final Map<Integer, Set<Integer>> anonymizedLocalOoiMap;

    public SingleManualIdgasAnonymizer(SingleIocTable iocTable, IdgasSingleIdgasIocTableUpdater iocTableUpdater, SingleLocalOoiCollection localOoiCollection, Map<Integer, Set<Integer>> anonymizedLocalOoiMap) {
        super(iocTable, iocTableUpdater);
        this.anonymizedLocalOoiMap = anonymizedLocalOoiMap;
    }


    @Override
    protected SingleAnonymizedLocalOoiSet getSingleAnonymizedLocalOoiSet(SingleIdgasAnonymizableReport anonymizableReport, SingleLocalOoiCombination localOoiCombination) {
        int reportId = anonymizableReport.getId();
        Set<Integer> anonymizedLocalOoiSet = anonymizedLocalOoiMap.get(reportId);
        return new SingleAnonymizedLocalOoiSet(anonymizedLocalOoiSet);
    }
}
