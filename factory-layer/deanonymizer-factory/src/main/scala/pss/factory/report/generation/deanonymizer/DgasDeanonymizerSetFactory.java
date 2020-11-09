package pss.factory.report.generation.deanonymizer;


import pss.data.combination_table.DecodedMapTable;
import pss.data.combination_table.ValueKey;
import pss.data.oc_table.MultiOcTable;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.deanonymization.Deanonymizable;
import pss.deanonymization.dgas.SingleDgasDeanonymizer;
import pss.deanonymization.dgas.decodingMapTable.updating.SingleDgasDecodingTableUpdater;
import pss.deanonymization.idgas.MultiIdgasDeanonymizer;
import pss.deanonymization.idgas.MultiIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.octable.updating.SimpleMultiIdgasIdgasDecodingTableUpdater;
import pss.decodability.checking.IdealMultiIdgasDecodabilityChecker;
import pss.decodability.checking.MultiDecodabilityChecker;
import pss.exception.PssException;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DgasDeanonymizerSetFactory implements DeanonymizerSetFactory {
    protected final PssType pssType;

    public DgasDeanonymizerSetFactory(PssType pssType) {
        this.pssType = pssType;
    }

    @Override
    public DeanonymizerGroupMapper createDeanonymizer(Set<PssGroup> pssGroups) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleDgasAnonymizerGroupMapper(pssGroups);
            case MULTI:
                return createMultiDgasAnonymizerGroupMapper(pssType, pssGroups);
        }
        throw new PssException(String.format("anonymizerFactory is not implemented for pss type %s", pssDimensionType.toString()));
    }

    private DeanonymizerGroupMapper createSingleDgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Deanonymizable> deanonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            SingleLocalOoiCollection localOoiCollection = (SingleLocalOoiCollection) pssGroup.getLocalOoiCollection();
            Set<ValueKey> valueKeySet = createValueKeySet(localOoiCollection.getLocalOoiSet().size());
            DecodedMapTable<SingleLocalOoiCombination> decodedMapTable = DecodedMapTableFactory.createSingleDecodedMapTable(localOoiCollection, valueKeySet);
            SingleDgasDecodingTableUpdater decodingTableUpdater = new SingleDgasDecodingTableUpdater(decodedMapTable, localOoiCollection);
            SingleDgasDeanonymizer deanonymizer = new SingleDgasDeanonymizer(decodedMapTable, decodingTableUpdater, valueKeySet);
            deanonymizerMap.put(pssGroup, deanonymizer);
        }
        return new DeanonymizerGroupMapper(deanonymizerMap);
    }

    private Set<ValueKey> createValueKeySet(int size) {
        Set<ValueKey> valueKeySet = new HashSet<>();
        for (int i = 1; i <= size; i++) {
            valueKeySet.add(new ValueKey(i));
        }
        return valueKeySet;
    }

    private DeanonymizerGroupMapper createMultiDgasAnonymizerGroupMapper(PssType pssType, Set<PssGroup> pssGroups) {
        Map<PssGroup, Deanonymizable> deanonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            MultiOcTable ocTable = new MultiOcTable(pssType.getDimensionSet().size(), pssType.getDimensionSet());
            MultiIdgasDecodingTableUpdater ocTableUpdater = new SimpleMultiIdgasIdgasDecodingTableUpdater(ocTable);
            MultiDecodabilityChecker decodabilityChecker = new IdealMultiIdgasDecodabilityChecker(ocTable);
            MultiIdgasDeanonymizer anonymizer = new MultiIdgasDeanonymizer(ocTable, decodabilityChecker, ocTableUpdater);
            deanonymizerMap.put(pssGroup, anonymizer);
        }
        return new DeanonymizerGroupMapper(deanonymizerMap);
    }
}
