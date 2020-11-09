package pss.result.deanonymization;

import pss.data.oc_table.SingleOcTable;
import pss.report.finalreport.SingleFinalReport;

public class SingleDeanonymizationResult extends DeanonymizationResult<SingleOcTable, SingleFinalReport> {

    public SingleDeanonymizationResult(SingleFinalReport finalReport, SingleOcTable ocTable, int totalDecoded, boolean decodability) {
        super(finalReport, ocTable, totalDecoded, decodability);
    }
}
