package pss.deanonymization.idgas;

import pss.report.finalreport.FinalReport;

public interface IdgasDecodingTableUpdater<TFinalReport extends FinalReport> {
    void update(TFinalReport finalReport);
}
