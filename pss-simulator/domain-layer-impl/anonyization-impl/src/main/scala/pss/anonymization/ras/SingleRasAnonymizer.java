package pss.anonymization.ras;


import pss.anonymization.Anonymizable;
import pss.anonymization.ioctable.updater.ras.SingleRasOcTableUpdater;
import pss.data.anonymity.SingleRasAnonymity;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.anonymizable.SingleRasAnonymizableReport;
import pss.report.anonymizable.SingleRasAnonymizableReportData;
import pss.report.finalreport.SingleFinalReport;
import pss.result.anonymization.SingleRasAnonymizationResult;

public class SingleRasAnonymizer implements Anonymizable<SingleRasAnonymizableReport, SingleRasAnonymizationResult, SingleFinalReport> {

    protected final SingleOcTable ocTable;
    protected final SingleRasOcTableUpdater ocTableUpdater;

    public SingleRasAnonymizer(SingleOcTable ocTable, SingleRasOcTableUpdater ocTableUpdater) {
        this.ocTable = ocTable;
        this.ocTableUpdater = ocTableUpdater;
    }


    @Override
    public SingleRasAnonymizationResult anonymize(SingleRasAnonymizableReport anonymizableReport) {
        Value reportedValue = anonymizableReport.getValue();
        SingleRasAnonymizableReportData reportData = anonymizableReport.getReportData();
        SingleRasAnonymity anonymity = reportData.getAnonymity();
        SingleAnonymizedLocalOoiSet anonymizedOois = ocTableUpdater.getAnonymizedOois(reportedValue, anonymity);

        return new SingleRasAnonymizationResult(anonymizedOois, ocTable.cloneOcTable());
    }

    @Override
    public void updateIfRequired(SingleFinalReport finalReport) {
        Value reportedValue = finalReport.getValue();
        SingleLocalOoiCollection localOoiCollection = finalReport.getReportData().getLocalOoiCollection();
        SingleAnonymizedLocalOoiSet localOoiSet = new SingleAnonymizedLocalOoiSet(localOoiCollection.getLocalOoiSet());
        ocTableUpdater.update(reportedValue, localOoiSet);
    }
}
