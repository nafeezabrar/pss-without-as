package pss.result.adversary;

import pss.data.oc_table.MultiOcTable;
import pss.report.finalreport.MultiFinalReport;

import java.util.List;
import java.util.Map;

public class MultiAdversaryResultWithSingleOcTable extends AdversaryResultWithSingleOcTable<MultiOcTable, MultiFinalReport> {

    protected MultiAdversaryResultWithSingleOcTable(int reportedByAdversaryCount, int reportedValueCountByAdversary, int leakedReportCount, int totalDecodedValueCount, List leakedReports, Map<MultiFinalReport, MultiOcTable> multiFinalReportMultiOcTableMap, MultiOcTable finalOcTable) {
        super(reportedByAdversaryCount, reportedValueCountByAdversary, leakedReportCount, totalDecodedValueCount, leakedReports, finalOcTable);
    }
}
