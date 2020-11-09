package pss.factory.report.generation.anonymizer;

import pss.anonymization.Anonymizable;
import pss.anonymization.dgas.SingleAnonymizedCollectionGenerator;
import pss.anonymization.dgas.SingleDecodedMapTableOptimalUpdater;
import pss.anonymization.dgas.SingleDgasAnonymizer;
import pss.anonymization.idgas.MultiIdgasAnonymizer;
import pss.anonymization.ioctable.generation.MultiIocTableGenerator;
import pss.anonymization.ioctable.updater.idgas.IdgasMultiIdgasIocTableUpdater;
import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.ValueKey;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static pss.data.pss_type.PssType.PssDimensionType;

public class DgasAnonymizerFactory implements AnonymizerFactory {
    protected final PssType pssType;

    public DgasAnonymizerFactory(PssType pssType) {
        this.pssType = pssType;
    }

    @Override
    public AnonymizerGroupMapper createAnonymizer(Set<PssGroup> pssGroups) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleDgasAnonymizerGroupMapper(pssGroups);
            case MULTI:
                return createMultiDgasAnonymizerGroupMapper(pssGroups);
        }
        throw new PssException(String.format("anonymizerFactory is not implemented for pss type %s", pssDimensionType.toString()));
    }

    private AnonymizerGroupMapper createSingleDgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            SingleLocalOoiCollection localOoiCollection = (SingleLocalOoiCollection) pssGroup.getLocalOoiCollection();
            Set<ValueKey> valueKeySet = createValueKeySet(localOoiCollection.getLocalOoiSet().size());
            DecodedMapTable<SingleLocalOoiCombination> decodedMapTable = DecodedMapTableFactory.createSingleDecodedMapTable(localOoiCollection, valueKeySet);
            SingleAnonymizedCollectionGenerator anonymizedCollectionGenerator = new SingleAnonymizedCollectionGenerator(localOoiCollection);
            SingleDecodedMapTableOptimalUpdater decodedMapTableOptimalUpdater = new SingleDecodedMapTableOptimalUpdater(decodedMapTable, localOoiCollection, valueKeySet);
            SingleDgasAnonymizer anonymizer = new SingleDgasAnonymizer(localOoiCollection, decodedMapTable, anonymizedCollectionGenerator, decodedMapTableOptimalUpdater);
            anonymizerMap.put(pssGroup, anonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }

    private Set<ValueKey> createValueKeySet(int size) {
        Set<ValueKey> valueKeySet = new HashSet<>();
        for (int i = 1; i <= size; i++) {
            valueKeySet.add(new ValueKey(i));
        }
        return valueKeySet;
    }

    private AnonymizerGroupMapper createMultiDgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        MultiIocTableGenerator iocTableGenerator = new MultiIocTableGenerator();
        for (PssGroup pssGroup : pssGroups) {
            MultiLocalOoiCollection localOoiCollection = (MultiLocalOoiCollection) pssGroup.getLocalOoiCollection();
            MultiOcTable iocTable = iocTableGenerator.generateIocTable(localOoiCollection);
            IdgasMultiIdgasIocTableUpdater iocTableUpdater = new IdgasMultiIdgasIocTableUpdater(iocTable);
            MultiIdgasAnonymizer anonymizer = new MultiIdgasAnonymizer(iocTable, iocTableUpdater, localOoiCollection);
            anonymizerMap.put(pssGroup, anonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }
}
