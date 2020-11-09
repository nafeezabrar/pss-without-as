package pss.anonymization.dgas;

import pss.anonymization.Anonymizable;
import pss.anonymization.AnonymizedCollectionGenerable;
import pss.anonymization.CombinationTableOptimalUpdatable;
import pss.data.combination_table.DecodedMapTable;
import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.report.anonymizable.AnonymizableReport;
import pss.report.finalreport.FinalReport;
import pss.result.anonymization.DgasAnonymizationResult;

public abstract class DgasAnonymizer<TAnonymizableReport extends AnonymizableReport, TAnonymizationResult extends DgasAnonymizationResult, TFinalReport extends FinalReport, TLocalOoiCombination extends LocalOoiCombination, TLocalOoiCollection extends LocalOoiCollection> implements Anonymizable<TAnonymizableReport, TAnonymizationResult, TFinalReport> {
    protected final TLocalOoiCollection localOoiCollection;
    protected final DecodedMapTable decodedMapTable;
    protected final AnonymizedCollectionGenerable anonymizedCollectionGenerator;
    protected final CombinationTableOptimalUpdatable<TLocalOoiCombination, TLocalOoiCollection> combinationTableOptimalUpdater;

    protected DgasAnonymizer(TLocalOoiCollection localOoiCollection, DecodedMapTable decodedMapTable, AnonymizedCollectionGenerable anonymizedCollectionGenerator, CombinationTableOptimalUpdatable combinationTableOptimalUpdater) {
        this.localOoiCollection = localOoiCollection;
        this.decodedMapTable = decodedMapTable;
        this.anonymizedCollectionGenerator = anonymizedCollectionGenerator;
        this.combinationTableOptimalUpdater = combinationTableOptimalUpdater;
    }


    public abstract TAnonymizationResult anonymize(TAnonymizableReport anonymizableReport);

}
