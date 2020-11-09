package pss.anonymization.aas;

import org.junit.Before;
import org.junit.Test;
import pss.anonymization.ioctable.updater.aas.SingleAasOcTableUpdater;
import pss.data.anonymity.SingleAasAnonymity;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.valuemap.Value;
import pss.library.MyRandom;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.anonymizable.SingleAasAnonymizableReport;
import pss.report.anonymizable.SingleAasAnonymizableReportData;
import pss.result.anonymization.SingleAasAnonymizationResult;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SingleAasAnonymizerTest {
    private final int totalOois = 6;
    private Map<Integer, Value> valueMap;
    private SingleLocalOoiCollection localOoiCollection;
    private SingleAasAnonymizer anonymizer;

    @Before
    public void setUp() {
        localOoiCollection = createOoiIdCollection(totalOois);
        anonymizer = createSingleAasAnonymizer();
    }

    private SingleLocalOoiCollection createOoiIdCollection(int totalOois) {
        Set<Integer> ooiIdSet = new HashSet<>();
        valueMap = new HashMap<>();
        int value = 10;
        for (int i = 0; i < totalOois; i++) {
            ooiIdSet.add(i);
            valueMap.put(i, new Value(value));
            value += 10;
        }

        return new SingleLocalOoiCollection(ooiIdSet);
    }

    private SingleAasAnonymizer createSingleAasAnonymizer() {
        SingleOcTable ocTable = new SingleOcTable();
        MyRandom random = new MyRandom(0);
        SingleAasOcTableUpdater ocTableUpdater = new SingleAasOcTableUpdater(ocTable, localOoiCollection, random);
        return new SingleAasAnonymizer(ocTable, ocTableUpdater);
    }

    private SingleAasAnonymizableReport createRandomAnonymizableReport() {
        Random random = new Random();
        int ooiId = localOoiCollection.getLocalOoiSet().iterator().next();
        int min, max;
        min = 2;
        max = totalOois - 2;
        int anonymity = random.nextInt((max - min) + 1) + min;
        min = 1;
        max = anonymity - 1;
        int addingAnonymity = random.nextInt((max - min) + 1) + min;
        return createAnonymizableReport(ooiId, anonymity, addingAnonymity);
    }

    private SingleAasAnonymizableReport createAnonymizableReport(int ooiId, int anonymity, int addingAnonymity) {
        Value value = valueMap.get(ooiId);
        SingleAasAnonymity aasAnonymity = new SingleAasAnonymity(anonymity, addingAnonymity);
        SingleAasAnonymizableReportData reportData = new SingleAasAnonymizableReportData(aasAnonymity);
        return new SingleAasAnonymizableReport(1, value, reportData);
    }

    @Test
    public void testAnonymizedReportHasValidLength() throws Exception {
        SingleAasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        SingleAasAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet = anonymizationResult.getAnonymizedLocalOoiSet();
        int anonymizedOois = anonymizedLocalOoiSet.size();
        SingleAasAnonymity aasAnonymity = anonymizableReport.getAnonymity();
        int requiredAnonymity = aasAnonymity.getAnonymity() - aasAnonymity.getAddingAnonymity();
        assertEquals(anonymizedOois, requiredAnonymity);
    }

    @Test
    public void testAnonymizedReportContainsValidOois() throws Exception {
        SingleAasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        SingleAasAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        Set<Integer> allOoiSet = new HashSet<>(localOoiCollection.getLocalOoiSet());
        Set<Integer> anonymizedOoiSet = new HashSet<>(anonymizationResult.getAnonymizedLocalOoiSet().getAnonymizedIdSet());
        assertTrue(allOoiSet.containsAll(anonymizedOoiSet));
    }
}