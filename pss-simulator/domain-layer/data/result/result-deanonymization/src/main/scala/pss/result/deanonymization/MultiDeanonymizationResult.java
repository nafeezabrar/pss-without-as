package pss.result.deanonymization;

import pss.data.oc_table.MultiOcTable;
import pss.report.finalreport.MultiFinalReport;

public class MultiDeanonymizationResult extends DeanonymizationResult<MultiOcTable, MultiFinalReport> {

    public MultiDeanonymizationResult(MultiFinalReport finalReport, MultiOcTable ocTable, int totalDecoded, boolean decodability) {
        super(finalReport, ocTable, totalDecoded, decodability);
    }
}
