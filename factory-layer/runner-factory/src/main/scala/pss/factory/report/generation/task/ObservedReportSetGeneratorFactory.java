package pss.factory.report.generation.task;

import pss.PrintingTargetType;
import pss.ReadingSourceType;
import pss.SavingTargetType;
import pss.config.printing.DefaultPrintingFileName;
import pss.config.printing.PrintConfig;
import pss.config.printing.ShouldPrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.config.task.FromFileObservedReportGenerationConfig;
import pss.config.task.ObservedReportGenerationConfig;
import pss.config.task.RandomObservedReportSetGenerationConfig;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.ReaderException;
import pss.exception.RunnerCreationException;
import pss.library.MyRandom;
import pss.random.RandomProvider;
import pss.reader.config.readers.JsonObservedReportDataReader;
import pss.reader.json.JsonFileReader;
import pss.reader.report.ObservedReportDataReadable;
import pss.report.generation.*;
import pss.result.presentable.printing.HtmlObservedReportPrinter;
import pss.result.presentable.printing.ObservedReportPrintable;
import pss.saving.observed_report.ObservedReportSetSavable;

import static java.lang.String.format;
import static pss.config.task.ObservedReportGenerationConfig.ReportGenerationMethod;
import static pss.data.pss_type.PssType.PssDimensionType;

public class ObservedReportSetGeneratorFactory {
    public static ObservedReportSetGenerable generateObservedReportGenerator(PssType pssType, ObservedReportGenerationConfig observedReportGenerationConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, PssException, InvalidConfigException, ReaderException {
        ObservedReportSetGenerable observedReportGenerator = generateReportGenerator(pssType, observedReportGenerationConfig, runnerSeed);
        SaveConfig saveConfig = observedReportGenerationConfig.getSaveConfig();
        if (saveConfig.shouldSave()) {
            observedReportGenerator = withSaver(observedReportGenerator, generateObservedReportSaver((ShouldSaveConfig) saveConfig));
        }
        PrintConfig printConfig = observedReportGenerationConfig.getPrintConfig();
        if (printConfig.shouldPrint()) {
            observedReportGenerator = withPrinter(observedReportGenerator, generateObservedReportPrinter((ShouldPrintConfig) printConfig, resultDirectory), pssType);
        }
        return observedReportGenerator;
    }

    private static ObservedReportSetGenerable generateReportGenerator(PssType pssType, ObservedReportGenerationConfig observedReportGenerationConfig, long runnerSeed) throws PssException, ReaderException {
        ReportGenerationMethod reportGenerationMethod = observedReportGenerationConfig.getReportGenerationMethod();
        switch (reportGenerationMethod) {

            case FROM_FILE:
                return generateFromFileObservedReportGenerator(pssType, (FromFileObservedReportGenerationConfig) observedReportGenerationConfig);
            case RANDOM:
                return generateRandomObservedReportGenerator(pssType, (RandomObservedReportSetGenerationConfig) observedReportGenerationConfig, runnerSeed);
        }
        throw new PssException(format("Report generator of type %s not matched", reportGenerationMethod.toString()));
    }

    private static ObservedReportSetGenerable generateFromFileObservedReportGenerator(PssType pssType, FromFileObservedReportGenerationConfig observedReportGenerationConfig) throws PssException, ReaderException {
        ObservedReportDataReadable observedReportDataReader = createObservedReportDataReader(observedReportGenerationConfig);
        return new FromFileObservedReportSetGenerator(observedReportDataReader, pssType);
    }

    private static ObservedReportDataReadable createObservedReportDataReader(FromFileObservedReportGenerationConfig observedReportGenerationConfig) throws PssException, ReaderException {
        ReadingSourceType readingSourceType = observedReportGenerationConfig.getReadingSourceType();
        switch (readingSourceType) {

            case CSV:
                throw new UnsupportedOperationException("CSV reader for observed report not impl");
            case JSON:
                return new JsonObservedReportDataReader(new JsonFileReader(observedReportGenerationConfig.getFileName()));
            case DB:
                throw new UnsupportedOperationException("DB reader for observed report not impl");
        }
        throw new PssException(format("Report reading source type %s not matched", readingSourceType.toString()));
    }

    private static ObservedReportSetGenerable withSaver(ObservedReportSetGenerable observedReportGenerator, ObservedReportSetSavable observedReportSaver) {
        return new ObservedReportSetGeneratorWithSaver(observedReportGenerator, observedReportSaver);
    }

    private static ObservedReportSetGenerable withPrinter(ObservedReportSetGenerable observedReportGenerator, ObservedReportPrintable observedReportPrinter, PssType pssType) {
        return new ObservedReportSetGeneratorWithPrinter(observedReportGenerator, observedReportPrinter, pssType);
    }

    private static ObservedReportSetGenerable generateRandomObservedReportGenerator(PssType pssType, RandomObservedReportSetGenerationConfig reportGenerationConfig, long runnerSeed) throws PssException {
        MyRandom myRandom = RandomProvider.getMyRandom(reportGenerationConfig.getRandomSource(), runnerSeed);
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        int reportCount = reportGenerationConfig.getReportCount();
        switch (pssDimensionType) {
            case SINGLE:
                return new RandomSingleObservedReportSetGenerator(myRandom, reportCount);
            case MULTI:
                return new RandomMultiObservedReportSetGenerator(myRandom, reportCount);
        }
        throw new UnsupportedOperationException(format("Random Observed report generator not implemented for %s", pssDimensionType.toString()));
    }

    private static ObservedReportSetSavable generateObservedReportSaver(ShouldSaveConfig saveConfig) throws InvalidConfigException {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(format("Observed Report saver not implemented for %s", savingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(format("Observed Report saver not implemented for %s", savingTargetType.toString()));
            case CSV:
                throw new UnsupportedOperationException(format("Observed Report saver not implemented for %s", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(format("Observed Report saver not implemented for %s", savingTargetType.toString()));
        }
        throw new InvalidConfigException(format("Observed Report saving target %s not recognized", savingTargetType.toString()));
    }

    private static ObservedReportPrintable generateObservedReportPrinter(ShouldPrintConfig printConfig, String resultDirectory) throws InvalidConfigException {
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String fileName = DefaultPrintingFileName.getObservedReportResultFileName(printConfig, resultDirectory);
        switch (printingTargetType) {
            case HTML:
                return new HtmlObservedReportPrinter(fileName);
            case CSV:
                throw new UnsupportedOperationException(format("Observed Report printer not implemented for %s", printingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(format("Observed Report printer not implemented for %s", printingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(format("Observed Report printer not implemented for %s", printingTargetType.toString()));
        }
        throw new InvalidConfigException(format("Observed Report printer target %s not recognized", printingTargetType.toString()));
    }
}
