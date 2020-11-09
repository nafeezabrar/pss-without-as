package pss.factory.report.generation.task;

import pss.config.task.DeanonymizationConfig;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.factory.report.generation.deanonymizer.DeanonymizerSetFactory;
import pss.factory.report.generation.deanonymizer.DgasDeanonymizerSetFactory;
import pss.factory.report.generation.deanonymizer.IdgasDeanonymizerSetFactory;

import static pss.config.task.DeanonymizationConfig.DeanonymizationMethod;

public class DeanonymizerFactoryFactory {
    public static DeanonymizerSetFactory generateDeanonymizerFactory(PssType pssType, DeanonymizationConfig deanonymizationConfig) throws RunnerCreationException, PssException {
        DeanonymizationMethod deanonymizationMethod = deanonymizationConfig.getDeanonymizationMethod();
        switch (deanonymizationMethod) {
            case IDGAS:
                return geenerateIdgasDeanonymizerFactory(pssType);
            case DGAS:
                return generateDgasDeanonymizerFactory(pssType);
        }
        throw new PssException(String.format("Deanonymizer for %s not recognized", deanonymizationMethod.toString()));
    }

    private static DeanonymizerSetFactory generateDgasDeanonymizerFactory(PssType pssType) {
        return new DgasDeanonymizerSetFactory(pssType);
    }

    private static DeanonymizerSetFactory geenerateIdgasDeanonymizerFactory(PssType pssType) {
        return new IdgasDeanonymizerSetFactory(pssType);
    }
}
