package pss.factory.report.generation.task;

import pss.PrintingTargetType;
import pss.SavingTargetType;
import pss.config.printing.DefaultPrintingFileName;
import pss.config.printing.PrintConfig;
import pss.config.printing.ShouldPrintConfig;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.config.task.MultiPssGroupingConfigOneDimension;
import pss.config.task.PssGroupingConfig;
import pss.data.dimension.Dimension;
import pss.data.ooi.Ooi;
import pss.data.pss_type.PssType;
import pss.exception.InvalidConfigException;
import pss.exception.PssException;
import pss.exception.RunnerCreationException;
import pss.library.SequentialIdGenerator;
import pss.result.presentable.printing.HtmlPssGroupPrinter;
import pss.result.presentable.printing.PssGroupPrintable;
import pss.saving.pss_group.PssGroupSavable;
import pss.variable.grouping.*;

import java.util.Map;

import static pss.config.task.PssGroupingConfig.PssGroupingMethod;

public class PssGroupingFactory {
    public static PssGroupable generatePssGroupable(PssType pssType, PssGroupingConfig pssGroupingConfig, String resultDirectory) throws RunnerCreationException, PssException, InvalidConfigException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        PssGroupable pssGrouper;
        switch (pssDimensionType) {
            case SINGLE:
                pssGrouper = generateSinglePssGrouper(pssGroupingConfig);
                break;
            case MULTI:
                pssGrouper = generateMultiPssGroupable(pssGroupingConfig);
                break;
            default:
                throw new PssException(String.format("pss grouping factory not implemented for %s pssType", pssDimensionType.toString()));
        }
        SaveConfig saveConfig = pssGroupingConfig.getSaveConfig();
        if (saveConfig.shouldSave()) {
            PssGroupSavable pssGroupSaver = generatePssGroupSaver((ShouldSaveConfig) saveConfig);
            pssGrouper = new PssGrouperWithSaver(pssGrouper, pssGroupSaver);
        }
        PrintConfig printConfig = pssGroupingConfig.getPrintConfig();
        if (printConfig.shouldPrint()) {
            PssGroupPrintable pssGroupPrinter = generatePssGroupPrinter((ShouldPrintConfig) printConfig, resultDirectory);
            pssGrouper = new PssGrouperWithPrinter(pssGrouper, pssGroupPrinter);
        }
        return pssGrouper;

    }

    private static PssGroupable generateSinglePssGrouper(PssGroupingConfig pssGroupingConfig) {
        return new SimpleSinglePssGrouper(new SequentialIdGenerator<>(), pssGroupingConfig.getOoiInEachGroupMap());
    }

    private static PssGroupable generateMultiPssGroupable(PssGroupingConfig pssGroupingConfig) throws PssException {
        PssGroupingMethod pssGroupingMethod = pssGroupingConfig.getPssGroupingMethod();
        Map<Dimension, Integer> ooiInEachGroupMap = pssGroupingConfig.getOoiInEachGroupMap();
        SequentialIdGenerator<Ooi> sequentialIdGenerator = new SequentialIdGenerator<>();
        switch (pssGroupingMethod) {
            case ONE_DIMENSION_GROUPING:
                MultiPssGroupingConfigOneDimension pssGroupingConfigOneDimension = (MultiPssGroupingConfigOneDimension) pssGroupingConfig;
                return new SimpleMultiPssGrouperByOneDimension(ooiInEachGroupMap, sequentialIdGenerator, pssGroupingConfigOneDimension.getDividingDimension());
            case ALL_DIMENSION_GROUPING:
                return new SimpleMultiPssGrouperByAllDimension(ooiInEachGroupMap, sequentialIdGenerator);
            case ALL_DIMENSION_SEQUENTIAL_GROUPING:
                return new SequentialMultiPssGrouperByAllDimension(ooiInEachGroupMap, sequentialIdGenerator);
        }
        throw new PssException(String.format("pss grouping factory not implemented for %s ", pssGroupingMethod.toString()));
    }

    private static PssGroupSavable generatePssGroupSaver(ShouldSaveConfig saveConfig) throws InvalidConfigException {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(String.format("Pss group saver not implemented for %s", savingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("Pss group saver not implemented for %s", savingTargetType.toString()));
            case CSV:
                throw new UnsupportedOperationException(String.format("Pss group saver not implemented for %s", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("Pss group saver not implemented for %s", savingTargetType.toString()));
        }
        throw new InvalidConfigException(String.format("Pss group saving target %s not recognized", savingTargetType.toString()));
    }

    private static PssGroupPrintable generatePssGroupPrinter(ShouldPrintConfig printConfig, String resultDirectory) throws InvalidConfigException {
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String fileName = DefaultPrintingFileName.getPssGroupingResultFileName(printConfig, resultDirectory);
        switch (printingTargetType) {
            case HTML:
                return new HtmlPssGroupPrinter(fileName);
            case CSV:
                throw new UnsupportedOperationException(String.format("Pss group printer not implemented for %s", printingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("Pss group printer not implemented for %s", printingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("Pss group printer not implemented for %s", printingTargetType.toString()));
        }
        throw new InvalidConfigException(String.format("Pss group printer target %s not recognized", printingTargetType.toString()));
    }
}
