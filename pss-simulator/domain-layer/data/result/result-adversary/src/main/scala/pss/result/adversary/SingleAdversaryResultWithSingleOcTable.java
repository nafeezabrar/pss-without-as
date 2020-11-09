package pss.result.adversary;

import pss.data.oc_table.SingleOcTable;
import pss.report.finalreport.SingleFinalReport;

import java.util.List;
import java.util.Map;

public class SingleAdversaryResultWithSingleOcTable extends AdversaryResultWithSingleOcTable<SingleOcTable, SingleFinalReport> {

    protected SingleAdversaryResultWithSingleOcTable(int reportedByAdversaryCount, int reportedValueCountByAdversary, int leakedReportCount, int totalDecodedValueCount, List leakedReports, Map<SingleFinalReport, SingleOcTable> singleFinalReportSingleOcTableMap, SingleOcTable finalOcTable) {
        super(reportedByAdversaryCount, reportedValueCountByAdversary, leakedReportCount, totalDecodedValueCount, leakedReports, finalOcTable);
    }
}
