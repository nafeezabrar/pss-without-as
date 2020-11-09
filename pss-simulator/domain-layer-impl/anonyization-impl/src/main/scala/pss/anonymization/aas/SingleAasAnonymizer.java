package pss.anonymization.aas;


import pss.anonymization.Anonymizable;
import pss.anonymization.ioctable.updater.aas.SingleAasOcTableUpdater;
import pss.data.anonymity.SingleAasAnonymity;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.anonymizable.SingleAasAnonymizableReport;
import pss.report.anonymizable.SingleAasAnonymizableReportData;
import pss.report.finalreport.SingleFinalReport;
import pss.result.anonymization.SingleAasAnonymizationResult;

public class SingleAasAnonymizer implements Anonymizable<SingleAasAnonymizableReport, SingleAasAnonymizationResult, SingleFinalReport> {

    protected final SingleOcTable ocTable;
    protected final SingleAasOcTableUpdater ocTableUpdater;

    public SingleAasAnonymizer(SingleOcTable ocTable, SingleAasOcTableUpdater ocTableUpdater) {
        this.ocTable = ocTable;
        this.ocTableUpdater = ocTableUpdater;
    }


    @Override
    public SingleAasAnonymizationResult anonymize(SingleAasAnonymizableReport anonymizableReport) {
        Value reportedValue = anonymizableReport.getValue();
        SingleAasAnonymizableReportData reportData = anonymizableReport.getReportData();
        SingleAasAnonymity anonymity = reportData.getAnonymity();
        SingleAnonymizedLocalOoiSet anonymizedOois = ocTableUpdater.getAnonymizedOois(reportedValue, anonymity);

        return new SingleAasAnonymizationResult(anonymizedOois, ocTable.cloneOcTable());
    }

    @Override
    public void updateIfRequired(SingleFinalReport finalReport) {
        Value reportedValue = finalReport.getValue();
        SingleLocalOoiCollection localOoiCollection = finalReport.getReportData().getLocalOoiCollection();
        SingleAnonymizedLocalOoiSet localOoiSet = new SingleAnonymizedLocalOoiSet(localOoiCollection.getLocalOoiSet());
        ocTableUpdater.update(reportedValue, localOoiSet);
    }
}
