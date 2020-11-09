package pss.factory.report.generation.task;

import com.github.javafaker.Faker;
import pss.PrintingTargetType;
import pss.SavingTargetType;
import pss.config.printing.DefaultPrintingFileName;
import pss.config.printing.PrintConfig;
import pss.config.printing.ShouldPrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.config.task.PssVariableGenerationConfig;
import pss.data.dimension.Dimension;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.library.MyRandom;
import pss.random.RandomProvider;
import pss.result.presentable.printing.HtmlPssVariablesPrinter;
import pss.result.presentable.printing.PssVariablesPrintable;
import pss.result.presentable.printing.TextFilePssVariablesPrinter;
import pss.saving.pss_variable.PssVariablesSavable;
import pss.saving.pss_variable.TextFilePssVariableSaver;
import pss.variable.generation.*;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static pss.config.task.PssVariableGenerationConfig.OoiGenerationMethod;
import static pss.data.pss_type.PssType.PssDimensionType;

public class PssVariableGeneratorFactory {
    public static PssVariableGenerable generatePssVariableGenerator(PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, long runnerSeed, String resultDirectory) throws RunnerCreationException, InvalidConfigException, PssException {
        PssVariableGenerable pssVariableGenerator = generatePssVariableGeneratorFromConfig(pssType, pssVariableGenerationConfig, runnerSeed);
        SaveConfig saveConfig = pssVariableGenerationConfig.getSaveConfig();
        if (saveConfig.shouldSave()) {
            PssVariablesSavable pssVariableSaver = generatePssVariableSaver((ShouldSaveConfig) saveConfig);
            pssVariableGenerator = new PssVariableGeneratorWithSaver(pssVariableGenerator, pssVariableSaver);
        }
        PrintConfig printConfig = pssVariableGenerationConfig.getPrintConfig();
        if (printConfig.shouldPrint()) {
            PssVariablesPrintable pssVariablesPrinter = generatePssVariablePrinter((ShouldPrintConfig) printConfig, resultDirectory);
            pssVariableGenerator = new PssVariableGeneratorWithPrinter(pssVariableGenerator, pssVariablesPrinter);
        }
        return pssVariableGenerator;
    }

    private static PssVariableGenerable generatePssVariableGeneratorFromConfig(PssType pssType, PssVariableGenerationConfig pssVariableGenerationConfig, long runnerSeed) throws PssException, InvalidConfigException {
        MyRandom myRandom = RandomProvider.getMyRandom(pssVariableGenerationConfig.getRandomSource(), runnerSeed);
        Map<Dimension, OoiGenerationMethod> pssVariableGenerationConfigMap = pssVariableGenerationConfig.getPssVariableGenerationConfigMap();

        Map<Dimension, OoiGenerable> ooiGeneratorMap = generateOoiGenerator(pssVariableGenerationConfigMap, myRandom);
        OoiValueGenerable ooiValueGenerator = generateOoiValueGenerator(pssVariableGenerationConfig.getOoiValueGenerationMethod(), pssType);
        return new PssVariableGenerator(ooiGeneratorMap, ooiValueGenerator);
    }

    private static OoiValueGenerable generateOoiValueGenerator(PssVariableGenerationConfig.OoiValueGenerationMethod ooiValueGenerationMethod, PssType pssType) throws InvalidConfigException {
        switch (ooiValueGenerationMethod) {

            case SEQUENTIAL:
                return createRandomOoiValueGenerator(pssType);
        }
        throw new InvalidConfigException(format("ooi value generator not recognized %s in factory", ooiValueGenerationMethod.toString()));
    }

    private static OoiValueGenerable createRandomOoiValueGenerator(PssType pssType) throws InvalidConfigException {
        PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {

            case SINGLE:
                return new SequentialSingleOoiValueGenerator();
            case MULTI:
                return new SequentialMultiOoiValueGenerator();
        }
        throw new InvalidConfigException(format("pss type recognized %s in factory", pssDimensionType.toString()));
    }

    private static Map<Dimension, OoiGenerable> generateOoiGenerator(Map<Dimension, OoiGenerationMethod> pssVariableGenerationConfigMap, MyRandom myRandom) throws PssException {

        Map<Dimension, OoiGenerable> ooiGeneratorMap = new HashMap<>();
        for (Dimension dimension : pssVariableGenerationConfigMap.keySet()) {
            OoiGenerationMethod ooiGenerationMethod = pssVariableGenerationConfigMap.get(dimension);
            OoiGenerable ooiGenerator = createOoiGenerator(ooiGenerationMethod, myRandom);
            ooiGeneratorMap.put(dimension, ooiGenerator);
        }
        return ooiGeneratorMap;
    }

    private static OoiGenerable createOoiGenerator(OoiGenerationMethod ooiGenerationMethod, MyRandom myRandom) throws PssException {
        switch (ooiGenerationMethod) {

            case FROM_FILE:
                throw new UnsupportedOperationException("not impl");
            case RANDOM:
                return new RandomOoiGeneratorUsingReadableWords(new Faker(myRandom));
            case ALPHABET_SMALL_LETTER:
                return new SmallLetterOoiGenerator();
            case ALPHABET_CAPITAL_LETTER:
                return new CapitalLetterOoiGenerator();
            case LOCATION_MIN_AREA_POINTS:
                return new LocationMinAreaOoiGenerator();
        }
        throw new PssException(format("Ooi generation method %s not matched", ooiGenerationMethod.toString()));
    }

    private static PssVariablesSavable generatePssVariableSaver(ShouldSaveConfig saveConfig) throws InvalidConfigException {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(format("Pss variable saver not implemented for %s", savingTargetType.toString()));
            case TEXT_FILE:
                return new TextFilePssVariableSaver(saveConfig.getTargetFileName());
            case CSV:
                throw new UnsupportedOperationException(format("Pss variable saver not implemented for %s", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(format("Pss variable saver not implemented for %s", savingTargetType.toString()));
        }
        throw new InvalidConfigException(format("Pss variable saving target %s not recognized", savingTargetType.toString()));
    }

    private static PssVariablesPrintable generatePssVariablePrinter(ShouldPrintConfig printConfig, String resultDirectory) throws InvalidConfigException {
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String fileName = DefaultPrintingFileName.getPssVariablesResultFileName(printConfig, resultDirectory);
        switch (printingTargetType) {
            case HTML:
                return new HtmlPssVariablesPrinter(fileName);
            case CSV:
                throw new UnsupportedOperationException(format("Pss variable printer not implemented for %s", printingTargetType.toString()));
            case TEXT_FILE:
                return new TextFilePssVariablesPrinter(fileName);
            case JSON:
                throw new UnsupportedOperationException(format("Pss variable printer not implemented for %s", printingTargetType.toString()));
        }
        throw new InvalidConfigException(format("Pss variable printer target %s not recognized", printingTargetType.toString()));
    }
}
