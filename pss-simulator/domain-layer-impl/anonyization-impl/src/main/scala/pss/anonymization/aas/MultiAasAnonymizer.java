package pss.anonymization.aas;

import pss.anonymization.Anonymizable;
import pss.anonymization.ioctable.updater.aas.MultiAasIocTableUpdater;
import pss.data.anonymity.MultiAasAnonymity;
import pss.data.oc_table.MultiOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.report.anonymizable.MultiAasAnonymizableReport;
import pss.report.anonymizable.MultiAasAnonymizableReportData;
import pss.report.finalreport.MultiFinalReport;
import pss.result.anonymization.MultiAasAnonymizationResult;

public class MultiAasAnonymizer implements Anonymizable<MultiAasAnonymizableReport, MultiAasAnonymizationResult, MultiFinalReport> {
    protected final MultiOcTable multiOcTable;
    protected final MultiAasIocTableUpdater ocTableUpdater;

    public MultiAasAnonymizer(MultiOcTable multiOcTable, MultiAasIocTableUpdater ocTableUpdater) {
        this.multiOcTable = multiOcTable;
        this.ocTableUpdater = ocTableUpdater;
    }

    @Override
    public MultiAasAnonymizationResult anonymize(MultiAasAnonymizableReport anonymizableReport) {
        Value reportedValue = anonymizableReport.getValue();
        MultiAasAnonymizableReportData reportData = anonymizableReport.getReportData();
        MultiAasAnonymity anonymity = reportData.getAnonymity();
        MultiAnonymizedLocalOoiSet anonymizedOois = ocTableUpdater.getAnonymizedOois(reportedValue, anonymity);

        return new MultiAasAnonymizationResult(anonymizedOois, multiOcTable.cloneOcTable());
    }

    @Override
    public void updateIfRequired(MultiFinalReport finalReport) {
        Value reportedValue = finalReport.getValue();
        MultiLocalOoiCollection localOoiCollection = finalReport.getReportData().getLocalOoiCollection();
        MultiAnonymizedLocalOoiSet localOoiSet = new MultiAnonymizedLocalOoiSet(localOoiCollection.getLocalOoiMap());
        ocTableUpdater.update(reportedValue, localOoiSet);
    }

}
