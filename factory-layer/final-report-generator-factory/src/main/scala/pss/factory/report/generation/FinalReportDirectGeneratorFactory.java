package pss.factory.report.generation;


import pss.config.task.FinalReportDirectGenerationConfig;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.report.generation.FinalReportDirectGenerable;

import static pss.config.task.FinalReportDirectGenerationConfig.FinalReportDirectGenerationMethod;

public class FinalReportDirectGeneratorFactory {
    public static FinalReportDirectGenerable generateFinalReportGenerator(FinalReportDirectGenerationConfig finalReportGenerationConfig) throws RunnerCreationException, PssException {
        FinalReportDirectGenerationMethod finalReportGenerationMethod = finalReportGenerationConfig.getFinalReportDirectGenerationMethod();
        switch (finalReportGenerationMethod) {
            case FROM_FILE:
                throw new UnsupportedOperationException("final report generation from file not implemented");
        }
        throw new PssException(String.format("final report generation method %s not recognized", finalReportGenerationMethod.toString()));
    }
}
