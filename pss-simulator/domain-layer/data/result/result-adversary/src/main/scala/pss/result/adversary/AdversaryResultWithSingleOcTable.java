package pss.result.adversary;

import pss.data.oc_table.OcTable;
import pss.report.finalreport.FinalReport;

import java.util.List;

public class AdversaryResultWithSingleOcTable<TOcTable extends OcTable, TFinalReport extends FinalReport> extends AdversaryResult {
    protected final TOcTable finalOcTable;

    public AdversaryResultWithSingleOcTable(int reportedByAdversaryCount, int reportedValueCountByAdversary, int leakedReportCount, int totalDecodedValueCount, List<TFinalReport> leakedReports, TOcTable finalOcTable) {
        super(reportedByAdversaryCount, reportedValueCountByAdversary, leakedReportCount, totalDecodedValueCount, leakedReports);
        this.finalOcTable = finalOcTable;
    }

    public TOcTable getFinalOcTable() {
        return finalOcTable;
    }
}
