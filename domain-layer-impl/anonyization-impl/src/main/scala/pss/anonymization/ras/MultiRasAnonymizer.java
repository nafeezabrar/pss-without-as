package pss.anonymization.ras;

import pss.anonymization.Anonymizable;
import pss.anonymization.ioctable.updater.ras.MultiRasOcTableUpdater;
import pss.data.anonymity.MultiRasAnonymity;
import pss.data.oc_table.MultiOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.report.anonymizable.MultiRasAnonymizableReport;
import pss.report.anonymizable.MultiRasAnonymizableReportData;
import pss.report.finalreport.MultiFinalReport;
import pss.result.anonymization.MultiRasAnonymizationResult;

public class MultiRasAnonymizer implements Anonymizable<MultiRasAnonymizableReport, MultiRasAnonymizationResult, MultiFinalReport> {
    protected final MultiOcTable multiOcTable;
    protected final MultiRasOcTableUpdater ocTableUpdater;

    public MultiRasAnonymizer(MultiOcTable multiOcTable, MultiRasOcTableUpdater ocTableUpdater) {
        this.multiOcTable = multiOcTable;
        this.ocTableUpdater = ocTableUpdater;
    }

    @Override
    public MultiRasAnonymizationResult anonymize(MultiRasAnonymizableReport anonymizableReport) {
        Value reportedValue = anonymizableReport.getValue();
        MultiRasAnonymizableReportData reportData = anonymizableReport.getReportData();
        MultiRasAnonymity anonymity = reportData.getAnonymity();
        MultiAnonymizedLocalOoiSet anonymizedOois = ocTableUpdater.getAnonymizedOois(reportedValue, anonymity);

        return new MultiRasAnonymizationResult(anonymizedOois, multiOcTable.cloneOcTable());
    }

    @Override
    public void updateIfRequired(MultiFinalReport finalReport) {
        Value reportedValue = finalReport.getValue();
        MultiLocalOoiCollection localOoiCollection = finalReport.getReportData().getLocalOoiCollection();
        MultiAnonymizedLocalOoiSet localOoiSet = new MultiAnonymizedLocalOoiSet(localOoiCollection.getLocalOoiMap());
        ocTableUpdater.update(reportedValue, localOoiSet);
    }

}
