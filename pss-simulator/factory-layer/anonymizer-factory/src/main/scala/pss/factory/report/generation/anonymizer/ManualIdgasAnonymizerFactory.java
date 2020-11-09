package pss.factory.report.generation.anonymizer;

import pss.ReadingSourceType;
import pss.anonymization.Anonymizable;
import pss.anonymization.idgas.MultiManualIdGasAnonymizer;
import pss.anonymization.idgas.SingleManualIdgasAnonymizer;
import pss.anonymization.ioctable.generation.MultiIocTableGenerator;
import pss.anonymization.ioctable.generation.SingleIocTableGenerator;
import pss.anonymization.ioctable.updater.idgas.IdgasMultiIdgasIocTableUpdater;
import pss.anonymization.ioctable.updater.idgas.IdgasSingleIdgasIocTableUpdater;
import pss.data.dimension.Dimension;
import pss.data.ioc_table.MultiOcTable;
import pss.data.ioc_table.SingleIocTable;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.group.PssGroup;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.mapper.anonymizer_group.AnonymizerGroupMapper;
import pss.reader.config.readers.JsonAnonymizedOoiReader;
import pss.reader.json.JsonFileReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static pss.data.pss_type.PssType.PssDimensionType;

public class ManualIdgasAnonymizerFactory implements AnonymizerFactory {
    protected final PssType pssType;
    protected final String fileName;
    protected final ReadingSourceType readingSourceType;
    protected final Set<Dimension> dimensions;

    public ManualIdgasAnonymizerFactory(PssType pssType, String fileName, ReadingSourceType readingSourceType, Set<Dimension> dimensions) {
        this.pssType = pssType;
        this.fileName = fileName;
        this.readingSourceType = readingSourceType;
        this.dimensions = dimensions;
    }

    @Override
    public AnonymizerGroupMapper createAnonymizer(Set<PssGroup> pssGroups) throws PssException, InvalidConfigException, ReaderException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return createSingleManualIdgasAnonymizerGroupMapper(pssGroups);
            case MULTI:
                return createMultiManualIdgasAnonymizerGroupMapper(pssGroups);
        }
        throw new PssException(format("anonymizerFactory is not implemented for pss type %s", pssDimensionType.toString()));
    }

    private AnonymizerGroupMapper createSingleManualIdgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        SingleIocTableGenerator iocTableGenerator = new SingleIocTableGenerator();
        for (PssGroup pssGroup : pssGroups) {
            SingleLocalOoiCollection localOoiCollection = (SingleLocalOoiCollection) pssGroup.getLocalOoiCollection();
            SingleIocTable singleIocTable = iocTableGenerator.generateIocTable(localOoiCollection);
            IdgasSingleIdgasIocTableUpdater iocTableUpdater = new IdgasSingleIdgasIocTableUpdater(singleIocTable);
            SingleManualIdgasAnonymizer anonymizer = new SingleManualIdgasAnonymizer(singleIocTable, iocTableUpdater, localOoiCollection, getSingleAnonymizedMapFromFile());
            anonymizerMap.put(pssGroup, anonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }

    private Map<Integer, Set<Integer>> getSingleAnonymizedMapFromFile() {
        throw new UnsupportedOperationException("Not impl");
    }

    private AnonymizerGroupMapper createMultiManualIdgasAnonymizerGroupMapper(Set<PssGroup> pssGroups) throws PssException, ReaderException, InvalidConfigException {
        Map<PssGroup, Anonymizable> anonymizerMap = new HashMap<>();
        MultiIocTableGenerator iocTableGenerator = new MultiIocTableGenerator();
        for (PssGroup pssGroup : pssGroups) {
            MultiLocalOoiCollection localOoiCollection = (MultiLocalOoiCollection) pssGroup.getLocalOoiCollection();
            MultiOcTable iocTable = iocTableGenerator.generateIocTable(localOoiCollection);
            IdgasMultiIdgasIocTableUpdater iocTableUpdater = new IdgasMultiIdgasIocTableUpdater(iocTable);
            MultiManualIdGasAnonymizer anonymizer = new MultiManualIdGasAnonymizer(iocTable, iocTableUpdater, localOoiCollection, getMultiAnonymizedMapFromFile());
            anonymizerMap.put(pssGroup, anonymizer);
        }
        return new AnonymizerGroupMapper(anonymizerMap);
    }

    private Map<Integer, Map<Dimension, Set<Integer>>> getMultiAnonymizedMapFromFile() throws PssException, InvalidConfigException, ReaderException {
        switch (readingSourceType) {

            case CSV:
                throw new UnsupportedOperationException("not impl");
            case JSON:
                return getMultiAnonymizedMapfromJsonFile();
            case DB:
                throw new UnsupportedOperationException("not impl");
        }
        throw new PssException(format("reading source type %s not recognized", readingSourceType.toString()));
    }

    private Map<Integer, Map<Dimension, Set<Integer>>> getMultiAnonymizedMapfromJsonFile() throws ReaderException, InvalidConfigException {
        JsonFileReader jsonFileReader = new JsonFileReader(fileName);
        Map<Integer, Map<Dimension, Set<Integer>>> anonymizedOoiMaps = JsonAnonymizedOoiReader.readMultiAnonymizedOoiMap(jsonFileReader, dimensions);
        return anonymizedOoiMaps;
    }
}
