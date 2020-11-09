package pss.anonymization.idgas;


import pss.anonymization.Anonymizable;
import pss.anonymization.ioctable.updater.idgas.IdgasMultiIdgasIocTableUpdater;
import pss.data.anonymity.MultiAnonymity;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.report.anonymizable.MultiIdgasAnonymizableReport;
import pss.report.finalreport.MultiFinalReport;
import pss.result.anonymization.MultiAnonymizationResult;

public class MultiIdgasAnonymizer implements Anonymizable<MultiIdgasAnonymizableReport, MultiAnonymizationResult, MultiFinalReport> {
    protected final MultiOcTable iocTable;
    protected final IdgasMultiIdgasIocTableUpdater iocTableUpdater;
    protected final MultiLocalOoiCollection localOoiCollection;

    public MultiIdgasAnonymizer(MultiOcTable iocTable, IdgasMultiIdgasIocTableUpdater iocTableUpdater, MultiLocalOoiCollection localOoiCollection) {
        this.iocTable = iocTable;
        this.iocTableUpdater = iocTableUpdater;
        this.localOoiCollection = localOoiCollection;
    }


    @Override
    public MultiAnonymizationResult anonymize(MultiIdgasAnonymizableReport anonymizableReport) {
        MultiLocalOoiCombination localOoiCombination = anonymizableReport.getReportData().getLocalOoiCombination();
        MultiAnonymizedLocalOoiSet anonymizedOois = getMultiAnonymizedLocalOoiSet(anonymizableReport, localOoiCombination);
        updateIocTable(localOoiCombination, anonymizedOois);
        return new MultiAnonymizationResult(anonymizedOois, iocTable.cloneIocTable());
    }

    @Override
    public void updateIfRequired(MultiFinalReport finalReport) {
        // do nothing
    }

    protected MultiAnonymizedLocalOoiSet getMultiAnonymizedLocalOoiSet(MultiIdgasAnonymizableReport anonymizableReport, MultiLocalOoiCombination localOoiCombination) {
        MultiAnonymity anonymity = anonymizableReport.getReportData().getAnonymity();
        return iocTableUpdater.getAnonymizedOois(localOoiCombination, anonymity);
    }

    private void updateIocTable(MultiLocalOoiCombination localOoiCombination, MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet) {
        iocTableUpdater.update(localOoiCombination, anonymizedLocalOoiSet);
    }
}
