package pss.deanonymization.dgas;

import pss.data.combination_table.ValueKey;
import pss.report.finalreport.FinalReport;

public interface DgasDecodingTableUpdatable<TFinalReport extends FinalReport> {
    void update(TFinalReport finalReport, ValueKey reportedValueKey);
}
