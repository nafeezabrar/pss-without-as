package pss.factory.report.generation.task;

import pss.ReadingSourceType;
import pss.config.InheritedRandomSource;
import pss.config.task.AnonymizationConfig;
import pss.config.task.ManualIdgasAnonymizationConfig;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.anonymizer.*;
import pss.library.MyRandom;
import pss.random.RandomProvider;

import static pss.config.task.AnonymizationConfig.AnonymizationMethod;

public class AnonymizerFactoryFactory {
    public static AnonymizerFactory generateAnonymizerFactory(PssType pssType, long runnerSeed, AnonymizationConfig anonymizationConfig) throws RunnerCreationException, PssException {
        AnonymizationMethod anonymizationMethod = anonymizationConfig.getAnonymizationMethod();
        switch (anonymizationMethod) {
            case IDGAS:
                return generateIdGasAnonymizerFactory(pssType);
            case IDGAS_FROM_FILE:
                return generateManualIdGasAnonymizerFactory(pssType, (ManualIdgasAnonymizationConfig) anonymizationConfig);
            case AAS:
                return generateAasAnonymizerFactory(pssType, runnerSeed);
            case RAS:
                return generateRasAnonymizerFactory(pssType, runnerSeed);
            case DGAS:
                return generateDgasAnonymizerFactory(pssType, runnerSeed);
        }
        throw new PssException(String.format("anonymizer is not implemented for method %s", anonymizationMethod.toString()));
    }

    private static AnonymizerFactory generateRasAnonymizerFactory(PssType pssType, long runnerSeed) throws PssException {
        MyRandom myRandom = RandomProvider.getMyRandom(new InheritedRandomSource(), runnerSeed);
        return new RasAnonymizerFactory(pssType, myRandom);
    }

    private static AnonymizerFactory generateDgasAnonymizerFactory(PssType pssType, long runnerSeed) {
        return new DgasAnonymizerFactory(pssType);
    }

    private static AnonymizerFactory generateManualIdGasAnonymizerFactory(PssType pssType, ManualIdgasAnonymizationConfig anonymizationConfig) {
        ReadingSourceType readingSourceType = anonymizationConfig.getReadingSourceType();
        String fileName = anonymizationConfig.getFileName();
        return new ManualIdgasAnonymizerFactory(pssType, fileName, readingSourceType, pssType.getDimensionSet());
    }

    private static AnonymizerFactory generateIdGasAnonymizerFactory(PssType pssType) {
        return new IdgasAnonymizerFactory(pssType);
    }

    private static AnonymizerFactory generateAasAnonymizerFactory(PssType pssType, long runnerSeed) throws PssException {
        MyRandom myRandom = RandomProvider.getMyRandom(new InheritedRandomSource(), runnerSeed);
        return new AasAnonymizerFactory(pssType, myRandom);
    }
}
