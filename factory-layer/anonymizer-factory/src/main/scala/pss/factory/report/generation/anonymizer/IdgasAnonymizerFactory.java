package pss.factory.report.generation.anonymizer;

import pss.anonymization.Anonymizable;
import pss.anonymization.idgas.MultiIdgasAnonymizer;
import pss.anonymization.idgas.SingleIdgasAnonymizer;
import pss.anonymization.ioctable.generation.MultiIocTableGenerator;
import pss.anonymization.ioctable.generation.SingleIocTableGenerator;
import pss.anonymization.ioctable.updater.idgas.IdgasMultiIdgasIocTableUpdater;
import pss.anonymization.ioctable.updater.idgas.IdgasSingleIdgasIocTableUpdater;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ioc_table.SingleIocTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.PssException;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static pss.data.pss_type.PssType.PssDimensionType;

public class IdgasAnonymizerFactory implements AnonymizerFactory {
    protected final PssType pssType;

    public IdgasAnonymizerFactory(PssType pssType) {
        this.pssType = pssType;
    }

    @Override
    public AnonymizerGroupMapper createAnonymizer(Set<PssGroup> pssGroups) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleIdgasAnonymizerGroupMapper(pssGroups);
            case MULTI:
                return createMultiIdgasAnonymizerGroupMapper(pssGroups);
        }
        throw new PssException(String.format("anonymizerFactory is not implemented for pss type %s", pssDimensionType.toString()));
    }

    private AnonymizerGroupMapper createSingleIdgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        SingleIocTableGenerator iocTableGenerator = new SingleIocTableGenerator();
        for (PssGroup pssGroup : pssGroups) {
            SingleLocalOoiCollection localOoiCollection = (SingleLocalOoiCollection) pssGroup.getLocalOoiCollection();
            SingleIocTable singleIocTable = iocTableGenerator.generateIocTable(localOoiCollection);
            IdgasSingleIdgasIocTableUpdater iocTableUpdater = new IdgasSingleIdgasIocTableUpdater(singleIocTable);
            SingleIdgasAnonymizer anonymizer = new SingleIdgasAnonymizer(singleIocTable, iocTableUpdater);
            anonymizerMap.put(pssGroup, anonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }

    private AnonymizerGroupMapper createMultiIdgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
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
