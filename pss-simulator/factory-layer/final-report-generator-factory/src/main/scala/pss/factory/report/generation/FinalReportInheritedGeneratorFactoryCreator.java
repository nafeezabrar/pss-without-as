package pss.factory.report.generation;

import pss.config.task.FaultyFinalReportInheritedGenerationConfig;
import pss.config.task.FinalReportInheritedGenerationConfig;
import pss.config.task.FinalReportInheritedGenerationConfig.FinalReportInheritedGenerationMethod;
import pss.data.pss_type.PssType;
import pss.exception.PssException;

import static pss.data.pss_type.PssType.PssDimensionType;

public class FinalReportInheritedGeneratorFactoryCreator {
    public static FinalReportInheritedGeneratorFactory generateFinalReportInheritedFactory(PssType pssType, FinalReportInheritedGenerationConfig finalReportInheritedGenerationConfig) throws PssException {
        FinalReportInheritedGenerationMethod finalReportInheritedGenerationMethod = finalReportInheritedGenerationConfig.getFinalReportInheritedGenerationMethod();
        switch (finalReportInheritedGenerationMethod) {
            case SIMPLE:
                return createSimpleFinalReportGeneratorFactory(pssType);
            case SIMPLE_FAULTY:
                return createSimpleFaultyFinalReportGeneratorFactory(pssType, (FaultyFinalReportInheritedGenerationConfig) finalReportInheritedGenerationConfig);
            case AAS_RANDOM:
                return createAasFinalReportGeneratorFactory(pssType);
            case RAS_RANDOM:
                return createRasFinalReportGeneratorFactory(pssType);
        }
        throw new PssException(String.format("final report generation method %s not matched", finalReportInheritedGenerationMethod.toString()));
    }

    private static FinalReportInheritedGeneratorFactory createRasFinalReportGeneratorFactory(PssType pssType) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleRasFinalReportInheritedGeneratorFactory();
            case MULTI:
                return new MultiRasFinalReportInheritedGeneratorFactory();
        }
        throw new PssException(String.format("pss dimension %s not matched", pssDimensionType.toString()));
    }

    private static FinalReportInheritedGeneratorFactory createSimpleFaultyFinalReportGeneratorFactory(PssType pssType, FaultyFinalReportInheritedGenerationConfig faultyReportGenerationConfig) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        double probability = faultyReportGenerationConfig.getProbability();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleSimpleFaultyFinalReportInheritedGeneratorFactory(probability);
            case MULTI:
                throw new UnsupportedOperationException("Not impl");
        }
        throw new PssException("dimension not matched");

    }

    private static FinalReportInheritedGeneratorFactory createAasFinalReportGeneratorFactory(PssType pssType) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleAasFinalReportInheritedGeneratorFactory();
            case MULTI:
                return new MultiAasFinalReportInheritedGeneratorFactory();
        }
        throw new PssException(String.format("pss dimension %s not matched", pssDimensionType.toString()));
    }

    private static FinalReportInheritedGeneratorFactory createSimpleFinalReportGeneratorFactory(PssType pssType) throws PssException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleSimpleFinalReportInheritedGeneratorFactory();
            case MULTI:
                return new MultiSimpleFinalReportInheritedGeneratorFactory();
        }
        throw new PssException(String.format("pss dimension %s not matched", pssDimensionType.toString()));
    }
}
