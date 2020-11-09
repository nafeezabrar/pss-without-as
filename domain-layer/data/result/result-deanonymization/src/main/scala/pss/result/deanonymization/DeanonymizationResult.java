package pss.result.deanonymization;

import pss.data.oc_table.OcTable;
import pss.report.finalreport.FinalReport;
import pss.result.Result;

public abstract class DeanonymizationResult<TOcTable extends OcTable, TFinalReport extends FinalReport> extends Result {
    protected final TFinalReport finalReport;
    protected final TOcTable ocTable;
    protected final int totalDecoded;
    protected final boolean isDecoded;

    protected DeanonymizationResult(TFinalReport finalReport, TOcTable ocTable, int totalDecoded, boolean isDecoded) {
        this.finalReport = finalReport;
        this.ocTable = ocTable;
        this.totalDecoded = totalDecoded;
        this.isDecoded = isDecoded;
    }


    public TOcTable getOcTable() {
        return ocTable;
    }

    public boolean isDecoded() {
        return isDecoded;
    }

    public TFinalReport getFinalReport() {
        return finalReport;
    }

    public int getTotalDecoded() {
        return totalDecoded;
    }
}
