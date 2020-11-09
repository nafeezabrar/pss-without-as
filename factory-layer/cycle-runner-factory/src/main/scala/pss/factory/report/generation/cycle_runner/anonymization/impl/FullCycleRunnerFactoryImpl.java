package pss.factory.report.generation.cycle_runner.anonymization.impl;

import pss.PrintingTargetType;
import pss.SavingTargetType;
import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.*;
import pss.config.runner.FullCycleRunnerConfig;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.data.decodability.DecodabilityCalculationPeriod;
import pss.data.decodability.DecodabilityType;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.ValueMap;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.decodability.calculation.EndPointApsFullDecodabilityCalculator;
import pss.decodability.calculation.IntermediatePointApsFullDecodabilityCalculator;
import pss.decodability.calculation.aps.EndPointDecodabilityCalculable;
import pss.decodability.calculation.aps.IntermediatePointsDecodabilityCalculable;
import pss.exception.PssException;
import pss.factory.report.generation.FinalReportInheritedGeneratorFactory;
import pss.factory.report.generation.cycle_runner.anonymization.FullCycleRunnerFactory;
import pss.mapper.deanonymizer_group.DeanonymizerGroupMapper;
import pss.mapper.user.UserMapper;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.generation.FinalReportInheritedGenerable;
import pss.result.presentable.printing.*;
import pss.runner.cycle.fullcycle.FullCycleRunner;
import pss.runner.cycle.impl.fullcycle.*;
import pss.saving.full_cycle_result.FullCycleResultSavable;

import java.util.*;

import static java.lang.String.format;
import static pss.config.printing.DecodabilityPrintConfig.PrintingSource;

public class FullCycleRunnerFactoryImpl implements FullCycleRunnerFactory {
    protected final long runnerSeed;
    protected final FullCycleRunnerConfig cycleRunnerConfig;
    protected final String resultDirectory;

    public FullCycleRunnerFactoryImpl(long runnerSeed, FullCycleRunnerConfig cycleRunnerConfig, String resultDirectory) {
        this.runnerSeed = runnerSeed;
        this.cycleRunnerConfig = cycleRunnerConfig;
        this.resultDirectory = resultDirectory;
    }

    @Override
    public FullCycleRunner generateCycleRunner(PssType pssType, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper, AnonymizerSelector anonymizerSelector, UserMapper userMapper, AnonymityGenerable anonymityGenerator, AnonymizableReportGenerable anonymizableReportGenerator, FinalReportInheritedGeneratorFactory finalReportInheritedGeneratorFactory, DeanonymizerGroupMapper deanonymizerGroupMapper, DeanonymizerSelector deanonymizerSelector, Set<PssGroup> pssGroups) throws PssException {
        FinalReportInheritedGenerable finalReportInheritedGenerator = finalReportInheritedGeneratorFactory.generateFinalReportGenerator(runnerSeed, pssVariables, ooiUserGroupMapper);
        FullCycleRunner fullCycleRunner = new FullCycleRunnerImpl(pssGroups, anonymizerSelector, deanonymizerSelector, userMapper, anonymityGenerator, anonymizableReportGenerator, finalReportInheritedGenerator);

        SaveConfig saveConfig = cycleRunnerConfig.getSaveConfig();
        if (saveConfig.shouldSave()) {
            fullCycleRunner = withSaver(pssType, ooiUserGroupMapper, fullCycleRunner, (ShouldSaveConfig) saveConfig);
        }
        PrintConfig printConfig = cycleRunnerConfig.getPrintConfig();
        if (printConfig.shouldPrint()) {
            fullCycleRunner = withPrinter(pssType, ooiUserGroupMapper, fullCycleRunner, (ShouldPrintConfig) printConfig);
        }
        fullCycleRunner = withCalculators(pssType, fullCycleRunner, cycleRunnerConfig.getDecodabilityCalculationConfig(), deanonymizerGroupMapper, ooiUserGroupMapper, pssVariables, printConfig, pssVariables.getValueMap(), pssGroups);
        return fullCycleRunner;
    }

    private FullCycleRunner withSaver(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, FullCycleRunner fullCycleRunner, ShouldSaveConfig saveConfig) throws PssException {
        FullCycleResultSavable fullCycleResultSaver = getFullCycleResultSaver(saveConfig);
        return new FullCycleRunnerWithSaver(fullCycleRunner, pssType, ooiUserGroupMapper, fullCycleResultSaver);
    }

    private FullCycleResultSavable getFullCycleResultSaver(ShouldSaveConfig saveConfig) throws PssException {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        String targetFileName = saveConfig.getTargetFileName();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(format("Full Cycle cycle runner saver not implemented for %s", savingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(format("Full Cycle cycle runner saver not implemented for %s", savingTargetType.toString()));
            case CSV:
                throw new UnsupportedOperationException(format("Full Cycle cycle runner saver not implemented for %s", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(format("Full Cycle cycle runner saver not implemented for %s", savingTargetType.toString()));
        }
        throw new PssException(format("Saving Target typeKey %s not recognized", savingTargetType.toString()));
    }

    private FullCycleRunner withPrinter(PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, FullCycleRunner fullCycleRunner, ShouldPrintConfig printConfig) throws PssException {
        FullCycleResultPrintable fullCycleResultPrinter = getFullCycleResultPrinter(printConfig);
        return new FullCycleRunnerWithPrinter(fullCycleRunner, pssType, ooiUserGroupMapper, fullCycleResultPrinter);
    }

    private FullCycleResultPrintable getFullCycleResultPrinter(ShouldPrintConfig printConfig) throws PssException {
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String targetFileName = DefaultPrintingFileName.getFullCycleResultFileName(printConfig, resultDirectory);
        switch (printingTargetType) {
            case HTML:
                return new HtmlFullCycleResultPrinter(targetFileName);
            case CSV:
                throw new UnsupportedOperationException(format("Full Cycle cycle runner printer not implemented for %s", printingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(format("Full Cycle cycle runner printer not implemented for %s", printingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(format("Full Cycle cycle runner printer not implemented for %s", printingTargetType.toString()));
        }
        throw new PssException(format("Saving Target typeKey %s not recognized", printingTargetType.toString()));
    }

    private FullCycleRunner withCalculators(PssType pssType, FullCycleRunner fullCycleRunner, DecodabilityCalculationConfig decodabilityCalculationConfig, DeanonymizerGroupMapper deanonymizerGroupMapper, OoiUserGroupMappable ooiUserGroupMapper, PssVariables pssVariables, PrintConfig fullCycleResultPrintConfig, ValueMap valueMap, Set<PssGroup> pssGroups) throws PssException {
        Map<DecodabilityCalculationPeriod, Set<DecodabilityType>> decodabilityMap = decodabilityCalculationConfig.getDecodabilityMap();
        Map<DecodabilityCalculationPeriod, DecodabilityPrintConfig> printConfigMap = decodabilityCalculationConfig.getDecodabilityPrintConfigMap();
        for (DecodabilityCalculationPeriod calculationPeriod : decodabilityMap.keySet()) {
            Set<DecodabilityType> decodabilityTypes = decodabilityMap.get(calculationPeriod);
            switch (calculationPeriod) {
                case INTERMEDIATE_POINTS_CALCULATION:
                    fullCycleRunner = withIntermediatePointsCalculators(fullCycleRunner, decodabilityTypes, printConfigMap.get(calculationPeriod), fullCycleResultPrintConfig, pssType, ooiUserGroupMapper, valueMap, pssGroups);
                    break;
                case END_POINT_CALCULATION:
                    fullCycleRunner = withEndPointCalculators(pssType, fullCycleRunner, printConfigMap.get(calculationPeriod), decodabilityTypes, deanonymizerGroupMapper, ooiUserGroupMapper, pssVariables.getValueMap());
                    break;
            }
        }
        return fullCycleRunner;
    }

    private FullCycleRunner withIntermediatePointsCalculators(FullCycleRunner fullCycleRunner, Set<DecodabilityType> decodabilityTypes, DecodabilityPrintConfig decodabilityPrintConfig, PrintConfig fullCycleResultPrintConfig, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, ValueMap valueMap, Set<PssGroup> pssGroups) throws PssException {
        if (decodabilityTypes.size() == 0) return fullCycleRunner;
        PrintingSource printingSource = decodabilityPrintConfig.getPrintingSource();
        switch (printingSource) {
            case INHERITED:
                return withInheritedIntermediateCalculators(pssType, pssGroups, fullCycleRunner, ooiUserGroupMapper, decodabilityTypes, valueMap, fullCycleResultPrintConfig);
            case SEPARATE:
                return withSeparateIntermediatePointCalculators(fullCycleRunner, decodabilityTypes, decodabilityPrintConfig);
        }

        throw new PssException(String.format("decodability print config %s not matched", printingSource.toString()));
    }

    private FullCycleRunner withSeparateIntermediatePointCalculators(FullCycleRunner fullCycleRunner, Set<DecodabilityType> decodabilityTypes, DecodabilityPrintConfig decodabilityPrintConfig) {
        throw new UnsupportedOperationException("not impl");
    }

    private FullCycleRunner withInheritedIntermediateCalculators(PssType pssType, Set<PssGroup> pssGroups, FullCycleRunner fullCycleRunner, OoiUserGroupMappable ooiUserGroupMapper, Set<DecodabilityType> decodabilityTypes, ValueMap valueMap, PrintConfig fullCycleResultPrintConfig) throws PssException {
        List<IntermediatePointsDecodabilityCalculable> decodabilityCalculators = new ArrayList<>();
        FullCycleResultWithIntermediateDecodabilityResultPrintable resultPrinter = getIntermediatePointFullCycleResultPrinter((ShouldPrintConfig) fullCycleResultPrintConfig);
        for (DecodabilityType decodabilityType : decodabilityTypes) {
            switch (decodabilityType) {
                case FULL:
                    Map<PssGroup, Integer> emptyDecodabilityMap = getEmptyDecodabilityMap(pssGroups);
                    IntermediatePointApsFullDecodabilityCalculator intermediatePointApsFullDecodabilityCalculator = new IntermediatePointApsFullDecodabilityCalculator(pssGroups, ooiUserGroupMapper, valueMap, emptyDecodabilityMap, pssType);
                    decodabilityCalculators.add(intermediatePointApsFullDecodabilityCalculator);
                    break;
                case PARTIAL:
                    throw new UnsupportedOperationException(String.format("intermediate decodability calculator %s not impl", decodabilityType.toString()));

            }
        }

        fullCycleRunner = new FullCycleRunnerWithPrinterWithIntermediatePointsCalculator(fullCycleRunner, pssType, ooiUserGroupMapper, decodabilityCalculators, resultPrinter);
        return fullCycleRunner;
    }

    private Map<PssGroup, Integer> getEmptyDecodabilityMap(Set<PssGroup> pssGroups) {
        Map<PssGroup, Integer> emptyDecodabilityMap = new HashMap<>();
        for (PssGroup pssGroup : pssGroups) {
            emptyDecodabilityMap.put(pssGroup, 0);
        }
        return emptyDecodabilityMap;
    }

    private FullCycleResultWithIntermediateDecodabilityResultPrintable getIntermediatePointFullCycleResultPrinter(ShouldPrintConfig fullCycleResultPrintConfig) throws PssException {
        String targetFileName = DefaultPrintingFileName.getIntermediateAdversaryDecodabilityResultFileName(fullCycleResultPrintConfig, resultDirectory);
        PrintingTargetType printingTargetType = fullCycleResultPrintConfig.getPrintingTargetType();
        switch (printingTargetType) {
            case HTML:
                return new HtmlFullCycleResultWithIntermediateDecodabilityResultPrinter(targetFileName);
            case CSV:
                throw new UnsupportedOperationException("not impl");
            case TEXT_FILE:
                throw new UnsupportedOperationException("not impl");
            case JSON:
                throw new UnsupportedOperationException("not impl");
        }
        throw new PssException(String.format("printing target type for decodability(with full cycle) %s not matched", printingTargetType.toString()));
    }

    private FullCycleRunner withEndPointCalculators(PssType pssType, FullCycleRunner fullCycleRunner, DecodabilityPrintConfig decodabilityPrintConfig, Set<DecodabilityType> decodabilityTypes, DeanonymizerGroupMapper deanonymizerGroupMapper, OoiUserGroupMappable ooiUserGroupMapper, ValueMap valueMap) throws PssException {
        if (decodabilityTypes.size() == 0) {
            return fullCycleRunner;
        }
        EndPointDecodabilityPrintable decodabilityPrinter = getEndPointDecodabilityPrinter(decodabilityPrintConfig);
        List<EndPointDecodabilityCalculable> endPointDecodabilityCalculators = new ArrayList<>();
        for (DecodabilityType decodabilityType : decodabilityTypes) {
            switch (decodabilityType) {
                case FULL:
                    EndPointApsFullDecodabilityCalculator calculator = new EndPointApsFullDecodabilityCalculator(deanonymizerGroupMapper, ooiUserGroupMapper, valueMap);
                    endPointDecodabilityCalculators.add(calculator);
                    break;

                case PARTIAL:
                    throw new UnsupportedOperationException(String.format("intermediate decodability calculator %s not impl", decodabilityType.toString()));

            }
        }
        if (endPointDecodabilityCalculators.size() != 0) {
            fullCycleRunner = new FullCycleRunnerWithEndPointCalculator(fullCycleRunner, pssType, ooiUserGroupMapper, endPointDecodabilityCalculators, decodabilityPrinter);
        }
        return fullCycleRunner;
    }

    private EndPointDecodabilityPrintable getEndPointDecodabilityPrinter(DecodabilityPrintConfig decodabilityPrintConfig) throws PssException {
        PrintingSource printingSource = decodabilityPrintConfig.getPrintingSource();
        switch (printingSource) {
            case INHERITED:
                throw new UnsupportedOperationException("not impl");
            case SEPARATE:
                return seperateEndPointDecodabilityPrinter((SeperateDecodabilityPrintConfig) decodabilityPrintConfig);
        }
        throw new PssException(String.format("print config %s not found", printingSource.toString()));
    }

    private EndPointDecodabilityPrintable seperateEndPointDecodabilityPrinter(SeperateDecodabilityPrintConfig decodabilityPrintConfig) throws PssException {
        ShouldPrintConfig printConfig = decodabilityPrintConfig.getPrintConfig();
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String fileName = DefaultPrintingFileName.getEndPointAdversaryDecodabilityPrintFileName(resultDirectory);
        String decodedValueFileName = DefaultPrintingFileName.getAdversaryDecodedValueCountFileName(resultDirectory);
        switch (printingTargetType) {
            case HTML:
                throw new UnsupportedOperationException("not impl");
            case CSV:
                throw new UnsupportedOperationException("not impl");
            case TEXT_FILE:
                return new TextFileSeparateEndPointDecodabilityPrinter(fileName, decodedValueFileName, printConfig.getAppendMode());
            case JSON:
                throw new UnsupportedOperationException("not impl");
        }
        throw new PssException(String.format("print target type %s not matched", printingTargetType.toString()));
    }
}
