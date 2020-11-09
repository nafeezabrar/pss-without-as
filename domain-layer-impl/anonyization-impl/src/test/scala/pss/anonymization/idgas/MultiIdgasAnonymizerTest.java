package pss.anonymization.idgas;

import org.junit.Before;
import org.junit.Test;
import pss.anonymization.ioctable.generation.MultiIocTableGenerator;
import pss.anonymization.ioctable.updater.idgas.IdgasMultiIdgasIocTableUpdater;
import pss.data.anonymity.MultiAnonymity;
import pss.data.dimension.Dimension;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.valuemap.Value;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.report.anonymizable.MultiIdgasAnonymizableReport;
import pss.report.anonymizable.MultiIdgasAnonymizableReportData;
import pss.result.anonymization.MultiAnonymizationResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MultiIdgasAnonymizerTest {
    private Set<Dimension> dimensions;
    private Dimension d1, d2;
    private Set<Integer> d1OoiIds;
    private Set<Integer> d2OoiIds;
    private MultiLocalOoiCollection localOoiCollection;
    private Set<MultiLocalOoiCombination> ooiIdCombinations;
    private MultiIdgasAnonymizer anonymizer;
    private MultiIocTableGenerator iocTableGenerator;

    @Before
    public void setUp() {
        localOoiCollection = createLocalOoiCollection();
        ooiIdCombinations = createOoiIdCombinations();
        iocTableGenerator = new MultiIocTableGenerator();
        anonymizer = createSingleIdGasAnonymizer();
    }

    private Set<MultiLocalOoiCombination> createOoiIdCombinations() {
        Set<MultiLocalOoiCombination> ooiIdCombinationSet = new HashSet<>();
        Map<Dimension, Integer> ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, 11);
        ooiIdMap.put(d2, 21);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, 11);
        ooiIdMap.put(d2, 22);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, 11);
        ooiIdMap.put(d2, 23);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();

        ooiIdMap.put(d1, 12);
        ooiIdMap.put(d2, 21);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, 12);
        ooiIdMap.put(d2, 22);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, 12);
        ooiIdMap.put(d2, 23);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();

        ooiIdMap.put(d1, 13);
        ooiIdMap.put(d2, 21);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, 13);
        ooiIdMap.put(d2, 22);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, 13);
        ooiIdMap.put(d2, 23);
        ooiIdCombinationSet.add(new MultiLocalOoiCombination(ooiIdMap));
        return ooiIdCombinationSet;
    }

    private MultiLocalOoiCollection createLocalOoiCollection() {
        dimensions = new HashSet<>();
        d1 = new Dimension(1, "d1", false);
        d2 = new Dimension(2, "d2", false);
        dimensions.add(d1);
        dimensions.add(d2);
        d1OoiIds = new HashSet<>();
        d1OoiIds.add(11);
        d1OoiIds.add(12);
        d1OoiIds.add(13);

        d2OoiIds = new HashSet<>();
        d2OoiIds.add(21);
        d2OoiIds.add(22);
        d2OoiIds.add(23);
        Map<Dimension, Set<Integer>> ooiIdMap = new HashMap<>();
        ooiIdMap.put(d1, d1OoiIds);
        ooiIdMap.put(d2, d2OoiIds);

        return new MultiLocalOoiCollection(ooiIdMap);
    }

    private MultiIdgasAnonymizer createSingleIdGasAnonymizer() {
        MultiOcTable iocTable = iocTableGenerator.generateIocTable(localOoiCollection);
        IdgasMultiIdgasIocTableUpdater iocTableUpdater = new IdgasMultiIdgasIocTableUpdater(iocTable);
        return new MultiIdgasAnonymizer(iocTable, iocTableUpdater, localOoiCollection);
    }

    private MultiIdgasAnonymizableReport createRandomAnonymizableReport() {
        Map<Dimension, Integer> ooiIdMap = new HashMap<>();
        Map<Dimension, Integer> anonymityMap = new HashMap<>();
        for (Dimension dimension : dimensions) {
            int ooiId = localOoiCollection.getLocalOoiMap().get(dimension).iterator().next();
            int anonymity = 2;
            ooiIdMap.put(dimension, ooiId);
            anonymityMap.put(dimension, anonymity);
        }

        return createAnonymizableReport(ooiIdMap, anonymityMap);
    }

    private MultiIdgasAnonymizableReport createAnonymizableReport(Map<Dimension, Integer> ooiIdMap, Map<Dimension, Integer> anonymityMap) {
        MultiLocalOoiCombination localOoiCombination = new MultiLocalOoiCombination(ooiIdMap);
        MultiAnonymity anonymity = new MultiAnonymity(anonymityMap);
        MultiIdgasAnonymizableReportData anonymizableData = new MultiIdgasAnonymizableReportData(anonymity, localOoiCombination);
        return new MultiIdgasAnonymizableReport(1, new Value(10), anonymizableData);
    }

    @Test
    public void testAnonymizedReportHasValidLength() throws Exception {
        MultiIdgasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        MultiAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        assertNotNull(anonymizationResult);
        MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet = anonymizationResult.getAnonymizedLocalOoiSet();
        Map<Dimension, Set<Integer>> ooiIdMap = anonymizedLocalOoiSet.getAnonymizedOoiSets();
        MultiAnonymity anonymity = anonymizableReport.getReportData().getAnonymity();
        Map<Dimension, Integer> anonymityMap = anonymity.getAnonymityMap();

        for (Dimension dimension : ooiIdMap.keySet()) {
            Set<Integer> anonymizedOois = ooiIdMap.get(dimension);
            assertTrue(anonymizedOois.size() == anonymityMap.get(dimension));
        }
    }

    @Test
    public void testAnonymizedReportContainsRealOoi() throws Exception {
        MultiIdgasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        MultiAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet = anonymizationResult.getAnonymizedLocalOoiSet();
        MultiLocalOoiCombination ooiIdCombination = anonymizableReport.getReportData().getLocalOoiCombination();
        for (Dimension dimension : dimensions) {
            assertTrue(anonymizedLocalOoiSet.getAnonymizedOoiSets().get(dimension).contains(ooiIdCombination.getLocalOoiMap().get(dimension)));
        }
    }

    @Test
    public void testAnonymizedReportContainsValidOois() throws Exception {
        MultiIdgasAnonymizableReport anonymizableReport = createRandomAnonymizableReport();
        MultiAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        Map<Dimension, Set<Integer>> anonymizedOoiSets = anonymizationResult.getAnonymizedLocalOoiSet().getAnonymizedOoiSets();
        for (Dimension dimension : dimensions) {
            Set<Integer> allOoiSet = localOoiCollection.getLocalOoiMap().get(dimension);
            Set<Integer> anonymizedOoiSet = anonymizedOoiSets.get(dimension);
            assertTrue(allOoiSet.containsAll(anonymizedOoiSet));
        }
    }

    @Test
    public void testAnonymizerWorksOptimally() {
        Map<Dimension, Integer> ooiIdMap = new HashMap<>();
        Map<Dimension, Integer> anonymityMap = new HashMap<>();
        ooiIdMap.put(d1, 11);
        ooiIdMap.put(d2, 21);
        anonymityMap.put(d1, 2);
        anonymityMap.put(d2, 2);

        MultiIdgasAnonymizableReport anonymizableReport = createAnonymizableReport(ooiIdMap, anonymityMap);
        Map<Dimension, Set<Integer>> notIncludedOoiMap = new HashMap<>();
        for (Dimension dimension : dimensions) {
            notIncludedOoiMap.put(dimension, new HashSet<>());
        }
        for (int i = 0; i < 2; i++) {
            MultiAnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
            Map<Dimension, Set<Integer>> anonymizedLocalOoiSet = anonymizationResult.getAnonymizedLocalOoiSet().getAnonymizedOoiSets();
            for (Dimension dimension : dimensions) {
                Set<Integer> includedOois = anonymizedLocalOoiSet.get(dimension);
                Set<Integer> ooiSet = new HashSet<>(localOoiCollection.getLocalOoiMap().get(dimension));
                ooiSet.removeAll(includedOois);
                Set<Integer> notIncludedOois = notIncludedOoiMap.get(dimension);
                notIncludedOois.addAll(ooiSet);
                notIncludedOoiMap.put(dimension, notIncludedOois);
            }
        }

        for (Dimension dimension : dimensions) {
            Set<Integer> ooiSet = new HashSet<>(localOoiCollection.getLocalOoiMap().get(dimension));
            ooiSet.remove(anonymizableReport.getReportData().getLocalOoiCombination().getLocalOoiMap().get(dimension));
            assertEquals(ooiSet, notIncludedOoiMap.get(dimension));
        }
    }
}