package pss.anonymization.idgas;

import org.junit.Before;
import org.junit.Test;
import pss.anonymization.ioctable.generation.SingleIocTableGenerator;
import pss.anonymization.ioctable.updater.idgas.IdgasSingleIdgasIocTableUpdater;
import pss.data.anonymity.SingleAnonymity;
import pss.data.ioc_table.SingleIocTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.anonymizable.SingleIdgasAnonymizableReport;
import pss.report.anonymizable.SingleIdgasAnonymizableReportData;
import pss.result.anonymization.SingleIdgasAnonymizationResult;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SingleIdgasAnonymizerTest {
    private final int totalOois = 6;
    private SingleLocalOoiCollection localOoiCollection;
    private SingleIdgasAnonymizer anonymizer;
    private SingleIocTableGenerator iocTableGenerator;

    @Before
    public void setUp() {
        localOoiCollection = createOoiIdCollection(totalOois);
        iocTableGenerator = new SingleIocTableGenerator();
        anonymizer = createSingleIdGasAnonymizer();
    }

    private SingleLocalOoiCollection createOoiIdCollection(int totalOois) {
        Set<Integer> ooiIdSet = new HashSet<>();
        for (int i = 0; i < totalOois; i++)
            ooiIdSet.add(i);

        return new SingleLocalOoiCollection(ooiIdSet);
    }

    private SingleIdgasAnonymizer createSingleIdGasAnonymizer() {
        SingleIocTable iocTable = iocTableGenerator.generateIocTable(localOoiCollection);
        IdgasSingleIdgasIocTableUpdater iocTableUpdater = new IdgasSingleIdgasIocTableUpdater(iocTable);
        return new SingleIdgasAnonymizer(iocTable, iocTableUpdater);
    }

    private SingleIdgasAnonymizableReport createRandomAnonymizableReport() {
        Random random = new Random();

        int ooiId = localOoiCollection.getLocalOoiSet().iterator().next();
        int anonymity = random.nextInt(totalOois - 1) + 1;

        return createAnonymizableReport(ooiId, anonymity);
    }

    private SingleIdgasAnonymizableReport createAnonymizableReport(int ooiId, int anonymity) {
        SingleLocalOoiCombination localOoiCombination = new SingleLocalOoiCombination(ooiId);
        SingleAnonymity singleAnonymity = new SingleAnonymity(anonymity);
        SingleIdgasAnonymizableReportData anonymizableData = new SingleIdgasAnonymizableReportData(singleAnonymity, localOoiCombination);
        return new SingleIdgasAnonymizableReport(1, new Value(10), anonymizableData);
    }

    @Test
    public void testAnonymizedReportHasValidLength() throws Exception {
        SingleIdgasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        SingleIdgasAnonymizationResult singleIdgasAnonymizationResult = anonymizer.anonymize(anonymizableReport);
        SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet = singleIdgasAnonymizationResult.getAnonymizedLocalOoiSet();
        int anonymizedOois = anonymizedLocalOoiSet.size();
        assertEquals(anonymizedOois, anonymizableReport.getAnonymity().getValue());
    }

    @Test
    public void testAnonymizedReportContainsRealOoi() throws Exception {
        SingleIdgasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        SingleIdgasAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet = anonymizationResult.getAnonymizedLocalOoiSet();
        Set<Integer> anonymizedIdSet = anonymizedLocalOoiSet.getAnonymizedIdSet();
        assertTrue(anonymizedIdSet.contains(anonymizableReport.getLocalOoiCombination().getLocalOoi()));
    }

    @Test
    public void testAnonymizedReportContainsValidOois() throws Exception {
        SingleIdgasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        SingleIdgasAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        Set<Integer> allOoiSet = new HashSet<>(localOoiCollection.getLocalOoiSet());
        Set<Integer> anonymizedOoiSet = new HashSet<>(anonymizationResult.getAnonymizedLocalOoiSet().getAnonymizedIdSet());
        assertTrue(allOoiSet.containsAll(anonymizedOoiSet));
    }

    @Test
    public void testAnonymizerWorksOptimally() {
        int ooiId = localOoiCollection.getLocalOoiSet().iterator().next();

        int anonymity = 4;
        SingleIdgasAnonymizableReport anonymizableReport = createAnonymizableReport(ooiId, anonymity);
        Set<Integer> notIncludedOois = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            SingleIdgasAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
            Set<Integer> includeOois = anonymizationResult.getAnonymizedLocalOoiSet().getAnonymizedIdSet();
            Set<Integer> ooiSet = new HashSet<>(localOoiCollection.getLocalOoiSet());
            ooiSet.removeAll(includeOois);

            notIncludedOois.addAll(ooiSet);
        }

        Set<Integer> ooiSet = new HashSet<>(localOoiCollection.getLocalOoiSet());
        ooiSet.remove(anonymizableReport.getLocalOoiCombination().getLocalOoi());

        assertEquals(ooiSet, notIncludedOois);
    }
}