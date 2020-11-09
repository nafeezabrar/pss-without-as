package pss.deanonymization.idgas;

import org.junit.Before;
import org.junit.Test;
import pss.data.oc_table.OcCell;
import pss.data.oc_table.SingleOcRow;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.deanonymization.idgas.octable.updating.SimpleSingleIdgasIdgasDecodingTableUpdater;
import pss.decodability.checking.SingleDecodabilityChecker;
import pss.report.finalreport.SingleFinalReport;
import pss.report.finalreport.SingleFinalReportData;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SingleIdgasDeanonymizerTest {
    private SingleIdgasDeanonymizer deanonymizer;
    private int ooiOne, ooiTwo, ooiThree, ooiFour, ooiFive, ooiSix;
    private SingleFinalReport[] finalReports;
    private Value value = new Value(10);

    @Before
    public void setUp() throws Exception {
        initOois();
        initFinalReports();
    }

    private void initOois() {
        ooiOne = 1;
        ooiTwo = 2;
        ooiThree = 3;
        ooiFour = 4;
        ooiFive = 5;
        ooiSix = 6;
    }

    private void initFinalReports() {
        finalReports = new SingleFinalReport[3];
        Set<Integer> ooiIdSet = new HashSet<>();
        ooiIdSet.add(ooiOne);
        ooiIdSet.add(ooiTwo);
        ooiIdSet.add(ooiThree);
        ooiIdSet.add(ooiFour);
        SingleFinalReportData reportData = new SingleFinalReportData(1, new SingleLocalOoiCollection(ooiIdSet));
        finalReports[0] = new SingleFinalReport(1, value, reportData);

        ooiIdSet = new HashSet<>();
        ooiIdSet.add(ooiOne);
        ooiIdSet.add(ooiFour);
        ooiIdSet.add(ooiFive);
        ooiIdSet.add(ooiSix);
        reportData = new SingleFinalReportData(1, new SingleLocalOoiCollection(ooiIdSet));
        finalReports[1] = new SingleFinalReport(1, value, reportData);

        ooiIdSet = new HashSet<>();
        ooiIdSet.add(ooiOne);
        ooiIdSet.add(ooiTwo);
        ooiIdSet.add(ooiThree);
        ooiIdSet.add(ooiSix);
        reportData = new SingleFinalReportData(1, new SingleLocalOoiCollection(ooiIdSet));
        finalReports[2] = new SingleFinalReport(1, value, reportData);
    }

    @Test
    public void testDecoderCanDecodeCorrectly() throws Exception {
        SingleOcTable ocTable = new SingleOcTable();
        deanonymizer = new SingleIdgasDeanonymizer(ocTable, mock(SingleDecodabilityChecker.class), new SimpleSingleIdgasIdgasDecodingTableUpdater(ocTable));
        deanonymizer.deanonymize(finalReports[0]);
        checkOcRowCreated(deanonymizer.getOcTable());
        SingleOcRow ocRow = deanonymizer.getOcTable().getOcRowMap().get(value);
        assertEquals(getDecodedOoi(ocRow), -1);
        deanonymizer.deanonymize(finalReports[1]);
        assertEquals(getDecodedOoi(ocRow), -1);
        deanonymizer.deanonymize(finalReports[2]);
        int ooiId = getDecodedOoi(ocRow);
        assertNotNull(ooiId);
        assertEquals(ooiId, ooiOne);

    }

    private void checkOcRowCreated(SingleOcTable ocTable) {
        SingleOcRow ocRow = ocTable.getOcRowMap().get(value);
        assertTrue(ocRow != null);
    }

    private int getDecodedOoi(SingleOcRow ocRow) {
        int totalReportCount = ocRow.getTotalReportCount();
        int maxCount = 0;
        int decodedOoiId = -1;
        for (OcCell ocCell : ocRow.getOcCellMap().values()) {
            if (ocCell.getOcCount() == totalReportCount) {
                if (maxCount == totalReportCount) {
                    decodedOoiId = -1;
                    break;
                }
                maxCount = totalReportCount;
                decodedOoiId = ocCell.getOoiId();
            }
        }
        return decodedOoiId;
    }
}