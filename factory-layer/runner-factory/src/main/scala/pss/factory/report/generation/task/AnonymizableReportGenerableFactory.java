package pss.factory.report.generation.task;

import pss.config.task.AnonymizationConfig.AnonymizationMethod;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.report.generation.*;

public class AnonymizableReportGenerableFactory {
    public static AnonymizableReportGenerable generateAnonymizableReportGenerator(PssType pssType, AnonymizationMethod anonymizationMethod) throws RunnerCreationException, PssException {
        switch (anonymizationMethod) {
            case IDGAS:
                return getIdgasAnonymizableReportGenerable(pssType);
            case AAS:
                return getAasAnonymizableReportGenerable(pssType);
            case RAS:
                return getRasAnonymizableReportGenerable(pssType);
            case DGAS:
                return getIdgasAnonymizableReportGenerable(pssType);
        }
        throw new PssException(String.format("anonymization method %s not matched", anonymizationMethod.toString()));
    }

    private static AnonymizableReportGenerable getRasAnonymizableReportGenerable(PssType pssType) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleRasAnonymizableReportGeneratorWithAnonymity();
            case MULTI:
                return new MultiIRasAnonymizableReportGeneratorWithAnonymity();
        }

        throw new PssException(String.format("anonymizable report generator not implemented for %s", pssDimensionType.toString()));
    }

    private static AnonymizableReportGenerable getAasAnonymizableReportGenerable(PssType pssType) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleAasAnonymizableReportGeneratorWithAnonymity();
            case MULTI:
                return new MultiIAasAnonymizableReportGeneratorWithAnonymity();
        }

        throw new PssException(String.format("anonymizable report generator not implemented for %s", pssDimensionType.toString()));
    }

    private static AnonymizableReportGenerable getIdgasAnonymizableReportGenerable(PssType pssType) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleIdgasAnonymizableReportGeneratorWithAnonymity();
            case MULTI:
                return new MultiIdgasAnonymizableReportGeneratorWithAnonymity();
        }

        throw new PssException(String.format("anonymizable report generator not implemented for %s", pssDimensionType.toString()));
    }
}
