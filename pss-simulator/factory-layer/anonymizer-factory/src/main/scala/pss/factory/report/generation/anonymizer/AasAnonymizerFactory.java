package pss.factory.report.generation.anonymizer;

import pss.anonymization.Anonymizable;
import pss.anonymization.aas.MultiAasAnonymizer;
import pss.anonymization.aas.SingleAasAnonymizer;
import pss.anonymization.ioctable.updater.aas.MultiAasIocTableUpdater;
import pss.anonymization.ioctable.updater.aas.SingleAasOcTableUpdater;
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

public class AasAnonymizerFactory implements AnonymizerFactory {
    protected final PssType pssType;
    protected final MyRandom myRandom;

    public AasAnonymizerFactory(PssType pssType, MyRandom myRandom) {
        this.pssType = pssType;
        this.myRandom = myRandom;
    }

    @Override
    public AnonymizerGroupMapper createAnonymizer(Set<PssGroup> pssGroups) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleAasAnonymizerGroupMapper(pssGroups);
            case MULTI:
                return createMultiAasAnonymizerGroupMapper(pssGroups);
        }
        throw new PssException(String.format("anonymizerFactory is not implemented for pss type %s", pssDimensionType.toString()));
    }

    private AnonymizerGroupMapper createSingleAasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            SingleLocalOoiCollection localOoiCollection = (SingleLocalOoiCollection) pssGroup.getLocalOoiCollection();
            SingleOcTable ocTable = new SingleOcTable();
            SingleAasOcTableUpdater ocTableUpdater = new SingleAasOcTableUpdater(ocTable, localOoiCollection, myRandom);
            SingleAasAnonymizer anonymizer = new SingleAasAnonymizer(ocTable, ocTableUpdater);
            anonymizerMap.put(pssGroup, anonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }

    private AnonymizerGroupMapper createMultiAasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            MultiLocalOoiCollection localOoiCollection = (MultiLocalOoiCollection) pssGroup.getLocalOoiCollection();
            MultiOcTable ocTable = new MultiOcTable(pssType.getDimensionSet().size(), pssType.getDimensionSet());
            MultiAasIocTableUpdater iocTableUpdater = new MultiAasIocTableUpdater(ocTable, localOoiCollection, myRandom);
            MultiAasAnonymizer multiAasAnonymizer = new MultiAasAnonymizer(ocTable, iocTableUpdater);
            anonymizerMap.put(pssGroup, multiAasAnonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }
}
