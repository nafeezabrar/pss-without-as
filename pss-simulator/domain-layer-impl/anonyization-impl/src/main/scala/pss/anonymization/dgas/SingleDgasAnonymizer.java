package pss.anonymization.dgas;

import pss.anonymization.AnonymizedCollectionGenerable;
import pss.anonymization.CombinationTableOptimalUpdatable;
import pss.data.anonymity.SingleAnonymity;
import pss.data.combination_table.DecodedMapTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.report.anonymizable.SingleIdgasAnonymizableReport;
import pss.report.finalreport.SingleFinalReport;
import pss.result.anonymization.SingleDgasAnonymizationResult;

import java.util.Set;

public class SingleDgasAnonymizer extends DgasAnonymizer<SingleIdgasAnonymizableReport, SingleDgasAnonymizationResult, SingleFinalReport, SingleLocalOoiCombination, SingleLocalOoiCollection> {


    public SingleDgasAnonymizer(SingleLocalOoiCollection localOoiCollection, DecodedMapTable decodedMapTable, AnonymizedCollectionGenerable anonymizedCollectionGenerator, CombinationTableOptimalUpdatable combinationTableOptimalUpdater) {
        super(localOoiCollection, decodedMapTable, anonymizedCollectionGenerator, combinationTableOptimalUpdater);
    }

    @Override
    public SingleDgasAnonymizationResult anonymize(SingleIdgasAnonymizableReport anonymizableReport) {
        SingleLocalOoiCombination reportedOoiCombination = anonymizableReport.getLocalOoiCombination();
        SingleAnonymity anonymity = anonymizableReport.getAnonymity();
        Set<SingleLocalOoiCollection> possibleAnonymizations = anonymizedCollectionGenerator.generatePossibleAnonymization(reportedOoiCombination, anonymity);
        SingleLocalOoiCollection bestAnonymizedCollection = combinationTableOptimalUpdater.chooseBestAnonymizedCollectionAndUpdate(reportedOoiCombination, anonymizableReport.getValue(), possibleAnonymizations);
        Set<Integer> anonymizedSet = bestAnonymizedCollection.getLocalOoiSet();
        SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet = new SingleAnonymizedLocalOoiSet(anonymizedSet);
        SingleDgasAnonymizationResult anonymizationResult = new SingleDgasAnonymizationResult(anonymizedLocalOoiSet);
        return anonymizationResult;
    }

    @Override
    public void updateIfRequired(SingleFinalReport finalReport) {
    }
}
