package pss.deanonymization.idgas;

import org.junit.Before;
import org.junit.Test;
import pss.data.dimension.Dimension;
import pss.data.oc_table.MultiOcRow;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.OcCell;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.deanonymization.idgas.octable.updating.SimpleMultiIdgasIdgasDecodingTableUpdater;
import pss.decodability.checking.MultiDecodabilityChecker;
import pss.report.finalreport.MultiFinalReport;
import pss.report.finalreport.MultiFinalReportData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MultiIdgasDeanonymizerTest {
    private MultiIdgasDeanonymizer deanonymizer;
    private Set<Dimension> dimensions;
    private Dimension d1, d2;
    private int totalDimensions = 2;
    private int d1Ooi1, d1Ooi2, d1Ooi3, d2Ooi1, d2Ooi2, d2Ooi3;
    private MultiFinalReport[] finalReports;
    private Value value = new Value(10);

    @Before
    public void setUp() throws Exception {
        initDimensions();
        initOois();
        initFinalReports();
    }

    private void initDimensions() {
        dimensions = new HashSet<>();
        d1 = new Dimension(1, "d1", false);
        d2 = new Dimension(2, "d2", false);
        dimensions.add(d1);
        dimensions.add(d2);
    }

    private void initOois() {
        d1Ooi1 = 11;
        d1Ooi2 = 12;
        d1Ooi3 = 13;
        d2Ooi1 = 21;
        d2Ooi2 = 22;
        d2Ooi3 = 23;
    }

    private void initFinalReports() {
        finalReports = new MultiFinalReport[2];
        Set<Integer> ooiIdSet;

        Map<Dimension, Set<Integer>> ooiIdMap = new HashMap<>();
        ooiIdSet = new HashSet<>();
        ooiIdSet.add(d1Ooi1);
        ooiIdSet.add(d1Ooi2);
        ooiIdMap.put(d1, ooiIdSet);
        ooiIdSet = new HashSet<>();
        ooiIdSet.add(d2Ooi1);
        ooiIdSet.add(d2Ooi2);
        ooiIdMap.put(d2, ooiIdSet);
        MultiFinalReportData reportData = new MultiFinalReportData(1, new MultiLocalOoiCollection(ooiIdMap));
        finalReports[0] = new MultiFinalReport(1, value, reportData);

        ooiIdMap = new HashMap<>();
        ooiIdSet = new HashSet<>();
        ooiIdSet.add(d1Ooi1);
        ooiIdSet.add(d1Ooi3);
        ooiIdMap.put(d1, ooiIdSet);
        ooiIdSet = new HashSet<>();
        ooiIdSet.add(d2Ooi1);
        ooiIdSet.add(d2Ooi3);
        ooiIdMap.put(d2, ooiIdSet);
        reportData = new MultiFinalReportData(1, new MultiLocalOoiCollection(ooiIdMap));
        finalReports[1] = new MultiFinalReport(1, value, reportData);

    }

    @Test
    public void testDecoderCanDecodeCorrectly() throws Exception {
        MultiOcTable ocTable = new MultiOcTable(totalDimensions, dimensions);
        SimpleMultiIdgasIdgasDecodingTableUpdater ocTableUpdater = new SimpleMultiIdgasIdgasDecodingTableUpdater(ocTable);
        deanonymizer = new MultiIdgasDeanonymizer(ocTable, mock(MultiDecodabilityChecker.class), ocTableUpdater);
        deanonymizer.deanonymize(finalReports[0]);
        checkOcRowCreated(deanonymizer.getOcTable());
        MultiOcRow ocRow = deanonymizer.getOcTable().getOcRowMap().get(value);
        assertNull(getDecodedOoi(ocRow));
        deanonymizer.deanonymize(finalReports[1]);
        Map<Dimension, Integer> decodedOoi = getDecodedOoi(ocRow);
        assertNotNull(decodedOoi);
        assertTrue(decodedOoi.get(d1) == d1Ooi1);
        assertTrue(decodedOoi.get(d2) == d2Ooi1);

    }

    private void checkOcRowCreated(MultiOcTable ocTable) {
        MultiOcRow ocRow = ocTable.getOcRowMap().get(value);
        assertTrue(ocRow != null);
    }

    private Map<Dimension, Integer> getDecodedOoi(MultiOcRow ocRow) {
        int totalReportCount = ocRow.getTotalReportCount();
        int maxCount;
        Map<Dimension, Integer> decodedOoiIds = new HashMap<>();
        int decodedOoiId;
        for (Dimension dimension : dimensions) {
            maxCount = 0;
            decodedOoiId = -1;
            for (OcCell ocCell : ocRow.getOcCellMap().get(dimension).values()) {
                if (ocCell.getOcCount() == totalReportCount) {
                    if (maxCount == totalReportCount) {
                        decodedOoiId = -1;
                        break;
                    }
                    maxCount = totalReportCount;
                    decodedOoiId = ocCell.getOoiId();
                }
            }
            if (decodedOoiId == -1) {
                return null;
            }
            decodedOoiIds.put(dimension, decodedOoiId);
        }

        return decodedOoiIds;
    }
}