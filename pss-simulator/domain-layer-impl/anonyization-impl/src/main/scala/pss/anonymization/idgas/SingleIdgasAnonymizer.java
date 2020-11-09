package pss.anonymization.idgas;


import pss.anonymization.Anonymizable;
import pss.anonymization.ioctable.updater.idgas.IdgasSingleIdgasIocTableUpdater;
import pss.data.anonymity.SingleAnonymity;
import pss.data.ioc_table.SingleIocTable;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.anonymizable.SingleIdgasAnonymizableReport;
import pss.report.finalreport.SingleFinalReport;
import pss.result.anonymization.SingleIdgasAnonymizationResult;

public class SingleIdgasAnonymizer implements Anonymizable<SingleIdgasAnonymizableReport, SingleIdgasAnonymizationResult, SingleFinalReport> {

    protected final SingleIocTable iocTable;
    protected final IdgasSingleIdgasIocTableUpdater iocTableUpdater;

    public SingleIdgasAnonymizer(SingleIocTable iocTable, IdgasSingleIdgasIocTableUpdater iocTableUpdater) {
        this.iocTable = iocTable;
        this.iocTableUpdater = iocTableUpdater;
    }


    @Override
    public SingleIdgasAnonymizationResult anonymize(SingleIdgasAnonymizableReport anonymizableReport) {
        SingleLocalOoiCombination localOoiCombination = anonymizableReport.getLocalOoiCombination();
        SingleAnonymizedLocalOoiSet anonymizedOois = getSingleAnonymizedLocalOoiSet(anonymizableReport, localOoiCombination);
        updateIocTable(localOoiCombination, anonymizedOois);
        return new SingleIdgasAnonymizationResult(anonymizedOois, iocTable.cloneIocTable());
    }

    @Override
    public void updateIfRequired(SingleFinalReport finalReport) {
        // do nothing
    }

    protected SingleAnonymizedLocalOoiSet getSingleAnonymizedLocalOoiSet(SingleIdgasAnonymizableReport anonymizableReport, SingleLocalOoiCombination localOoiCombination) {
        SingleAnonymity anonymity = anonymizableReport.getAnonymity();
        return this.iocTableUpdater.getAnonymizedOois(localOoiCombination, anonymity);
    }

    private void updateIocTable(SingleLocalOoiCombination localOoiCombination, SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet) {
        iocTableUpdater.update(localOoiCombination, anonymizedLocalOoiSet);
    }
}
