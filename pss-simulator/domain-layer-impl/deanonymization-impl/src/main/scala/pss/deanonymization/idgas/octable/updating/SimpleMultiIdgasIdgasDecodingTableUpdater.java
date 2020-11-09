package pss.deanonymization.idgas.octable.updating;

import pss.data.dimension.Dimension;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.deanonymization.idgas.MultiIdgasDecodingTableUpdater;
import pss.report.finalreport.MultiFinalReport;

import java.util.Map;
import java.util.Set;

public class SimpleMultiIdgasIdgasDecodingTableUpdater implements MultiIdgasDecodingTableUpdater {
    protected final MultiOcTable ocTable;

    public SimpleMultiIdgasIdgasDecodingTableUpdater(MultiOcTable ocTable) {
        this.ocTable = ocTable;
    }

    @Override
    public void update(MultiFinalReport finalReport) {
        ensureOcRow(finalReport.getValue());
        updateOcTable(finalReport);

    }

    private void updateOcTable(MultiFinalReport finalReport) {
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        MultiOcRow ocRow = ocRowMap.get(finalReport.getValue());
        updateTotalReportCount(ocRow);
        MultiLocalOoiCollection finalOoiIdCollection = finalReport.getReportData().getLocalOoiCollection();
        updateOcCounts(finalOoiIdCollection, ocRow);
    }

    private void ensureOcRow(Value value) {
        Map<Value, MultiOcRow> ocRowMap = ocTable.getOcRowMap();
        if (!ocRowMap.containsKey(value))
            ocRowMap.put(value, new MultiOcRow(value, ocTable.getDimensions()));
    }

    private void updateTotalReportCount(MultiOcRow ocRow) {
        ocRow.incrementTotalReportCount();
    }

    private void updateOcCounts(MultiLocalOoiCollection finalOoiIdCollection, MultiOcRow ocRow) {
        Set<Dimension> dimensions = finalOoiIdCollection.getLocalOoiMap().keySet();
        for (Dimension dimension : dimensions) {
            Set<Integer> reportedOois = finalOoiIdCollection.getLocalOoiMap().get(dimension);
            Map<Integer, OcCell> ocCellMap = ocRow.getOcCellMap().get(dimension);
            updateOcCountForOneDimension(reportedOois, ocCellMap);
        }
    }

    private void updateOcCountForOneDimension(Set<Integer> reportedOois, Map<Integer, OcCell> ocCellMap) {
        for (Integer ooiId : reportedOois) {
            if (!ocCellMap.containsKey(ooiId)) {
                ocCellMap.put(ooiId, new OcCell(ooiId));
            }
            ocCellMap.get(ooiId).incrementCount();
        }
    }
}
