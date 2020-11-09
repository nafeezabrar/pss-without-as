package pss.result.anonymization;

import pss.data.oc_table.OcTable;
import pss.local.ooi.anonymized.AnonymizedLocalOoiSet;

public class RasAnonymizationResult<TOcTable extends OcTable, TAnonymizedLocalOoiSet extends AnonymizedLocalOoiSet> extends AnonymizationResult<TAnonymizedLocalOoiSet> {
    protected final TOcTable ocTable;

    protected RasAnonymizationResult(TAnonymizedLocalOoiSet anonymizedLocalOoiSet, TOcTable ocTable) {
        super(anonymizedLocalOoiSet);
        this.ocTable = ocTable;
    }

    public TOcTable getOcTable() {
        return ocTable;
    }
}
