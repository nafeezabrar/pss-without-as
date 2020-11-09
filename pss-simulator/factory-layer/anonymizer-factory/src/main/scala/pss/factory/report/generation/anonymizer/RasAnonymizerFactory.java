package pss.factory.report.generation.anonymizer;

import pss.anonymization.Anonymizable;
import pss.anonymization.ioctable.updater.ras.MultiRasOcTableUpdater;
import pss.anonymization.ioctable.updater.ras.SingleRasOcTableUpdater;
import pss.anonymization.ras.MultiRasAnonymizer;
import pss.anonymization.ras.SingleRasAnonymizer;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.SingleOcTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static pss.data.pss_type.PssType.PssDimensionType;

public class RasAnonymizerFactory implements AnonymizerFactory {
    protected final PssType pssType;
    protected final MyRandom myRandom;

    public RasAnonymizerFactory(PssType pssType, MyRandom myRandom) {
        this.pssType = pssType;
        this.myRandom = myRandom;
    }

    @Override
    public AnonymizerGroupMapper createAnonymizer(Set<PssGroup> pssGroups) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleRasAnonymizerGroupMapper(pssGroups);
            case MULTI:
                return createMultiRasAnonymizerGroupMapper(pssGroups);
        }
        throw new PssException(String.format("anonymizerFactory is not implemented for pss type %s", pssDimensionType.toString()));
    }

    private AnonymizerGroupMapper createSingleRasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            SingleLocalOoiCollection localOoiCollection = (SingleLocalOoiCollection) pssGroup.getLocalOoiCollection();
            SingleOcTable ocTable = new SingleOcTable();
            SingleRasOcTableUpdater ocTableUpdater = new SingleRasOcTableUpdater(ocTable, localOoiCollection, myRandom);
            SingleRasAnonymizer anonymizer = new SingleRasAnonymizer(ocTable, ocTableUpdater);
            anonymizerMap.put(pssGroup, anonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }

    private AnonymizerGroupMapper createMultiRasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            MultiLocalOoiCollection localOoiCollection = (MultiLocalOoiCollection) pssGroup.getLocalOoiCollection();
            MultiOcTable ocTable = new MultiOcTable(pssType.getDimensionSet().size(), pssType.getDimensionSet());
            MultiRasOcTableUpdater ocTableUpdater = new MultiRasOcTableUpdater(ocTable, localOoiCollection, myRandom);
            MultiRasAnonymizer multiRasAnonymizer = new MultiRasAnonymizer(ocTable, ocTableUpdater);
            anonymizerMap.put(pssGroup, multiRasAnonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }
}
