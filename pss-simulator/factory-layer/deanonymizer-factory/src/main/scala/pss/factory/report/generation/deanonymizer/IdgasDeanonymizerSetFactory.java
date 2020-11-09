package pss.factory.report.generation.deanonymizer;


import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.SingleOcTable;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.deanonymization.Deanonymizable;
import pss.deanonymization.idgas.MultiIdgasDeanonymizer;
import pss.deanonymization.idgas.MultiIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.SingleIdgasDeanonymizer;
import pss.deanonymization.idgas.SingleIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.octable.updating.SimpleMultiIdgasIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.octable.updating.SimpleSingleIdgasIdgasDecodingTableUpdater;
import pss.decodability.checking.IdealMultiIdgasDecodabilityChecker;
import pss.decodability.checking.IdealSingleIdgasDecodabilityChecker;
import pss.decodability.checking.MultiDecodabilityChecker;
import pss.decodability.checking.SingleDecodabilityChecker;
import pss.exception.PssException;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IdgasDeanonymizerSetFactory implements DeanonymizerSetFactory {
    protected final PssType pssType;

    public IdgasDeanonymizerSetFactory(PssType pssType) {
        this.pssType = pssType;
    }

    @Override
    public DeanonymizerGroupMapper createDeanonymizer(Set<PssGroup> pssGroups) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleIdgasAnonymizerGroupMapper(pssGroups);
            case MULTI:
                return createMultiIdgasAnonymizerGroupMapper(pssType, pssGroups);
        }
        throw new PssException(String.format("anonymizerFactory is not implemented for pss type %s", pssDimensionType.toString()));
    }

    private DeanonymizerGroupMapper createSingleIdgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Deanonymizable> deanonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            SingleOcTable singleOcTable = new SingleOcTable();
            SingleIdgasDecodingTableUpdater ocTableUpdater = new SimpleSingleIdgasIdgasDecodingTableUpdater(singleOcTable);
            SingleDecodabilityChecker decodabilityChecker = new IdealSingleIdgasDecodabilityChecker(singleOcTable);
            SingleIdgasDeanonymizer anonymizer = new SingleIdgasDeanonymizer(singleOcTable, decodabilityChecker, ocTableUpdater);
            deanonymizerMap.put(pssGroup, anonymizer);
        }
        return new DeanonymizerGroupMapper(deanonymizerMap);
    }

    private DeanonymizerGroupMapper createMultiIdgasAnonymizerGroupMapper(PssType pssType, Set<PssGroup> pssGroups) {
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
