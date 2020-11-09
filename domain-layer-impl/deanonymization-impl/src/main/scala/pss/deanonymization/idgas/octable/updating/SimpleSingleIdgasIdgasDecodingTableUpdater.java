package pss.deanonymization.idgas.octable.updating;

import pss.data.oc_table.OcCell;
import pss.data.oc_table.SingleOcRow;
import pss.data.oc_table.SingleOcTable;
import pss.data.valuemap.Value;
import pss.deanonymization.idgas.SingleIdgasDecodingTableUpdater;
import pss.report.finalreport.SingleFinalReport;

import java.util.Map;
import java.util.Set;

public class SimpleSingleIdgasIdgasDecodingTableUpdater implements SingleIdgasDecodingTableUpdater {
    protected final SingleOcTable ocTable;

    public SimpleSingleIdgasIdgasDecodingTableUpdater(SingleOcTable ocTable) {
        this.ocTable = ocTable;
    }

    @Override
    public void update(SingleFinalReport finalReport) {
        ensureOcRow(ocTable, finalReport.getValue());
        updateOcTable(finalReport);
    }

    private void ensureOcRow(SingleOcTable ocTable, Value value) {
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
        if (!ocRowMap.containsKey(value))
            ocRowMap.put(value, new SingleOcRow(value));
    }

    private void updateOcTable(SingleFinalReport finalReport) {
        Map<Value, SingleOcRow> ocRowMap = ocTable.getOcRowMap();
        SingleOcRow ocRow = ocRowMap.get(finalReport.getValue());
        ocRow.incrementTotalReportCount();
        Set<Integer> reportedOois = finalReport.getReportData().getLocalOoiCollection().getLocalOoiSet();
        Map<Integer, OcCell> ocCellMap = ocRow.getOcCellMap();
        for (Integer ooiId : reportedOois) {
            if (!ocCellMap.containsKey(ooiId)) {
                ocCellMap.put(ooiId, new OcCell(ooiId));
            }
            ocCellMap.get(ooiId).incrementCount();
        }
    }
}
