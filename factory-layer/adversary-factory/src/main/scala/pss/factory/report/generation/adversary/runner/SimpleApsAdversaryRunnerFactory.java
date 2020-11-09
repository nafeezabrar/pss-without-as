package pss.factory.report.generation.adversary.runner;

import com.pss.adversary.ApsAdversary;
import com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod;
import com.pss.adversary.runner.*;
import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.PrintingTargetType;
import pss.SavingTargetType;
import pss.config.adversary.ApsAdversaryConfig;
import pss.config.decodability.result.DecodabilityCalculationConfig;
import pss.config.printing.*;
import pss.config.saving.SaveConfig;
import pss.config.saving.ShouldSaveConfig;
import pss.data.decodability.DecodabilityCalculationPeriod;
import pss.data.decodability.DecodabilityType;
import pss.data.oc_table.OcTable;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.valuemap.ValueMap;
import pss.deanonymization.idgas.IdgasDeanonymizer;
import pss.decodability.calculation.*;
import pss.decodability.calculation.aps.AdversaryEndPointDecodabilityCalculable;
import pss.decodability.calculation.aps.AdversaryIntermediatePointsDecodabilityCalculable;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.reader.octable.TextFileOcTableReaderWithIndex;
import pss.report.finalreport.FinalReport;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.*;
import pss.saving.OcTableSavable.OcTableSavable;
import pss.saving.octable.TextFileOcTableSaverInMultipleFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static pss.config.printing.ShouldPrintConfig.AppendMode.SHOULD_APPEND;

public class SimpleApsAdversaryRunnerFactory implements AdversaryRunnerFactory<ApsAdversary> {
    protected final ApsAdversaryConfig apsAdversaryConfig;
    protected final String resultDirectory;
    protected final MyRandom myRandom;

    public SimpleApsAdversaryRunnerFactory(ApsAdversaryConfig apsAdversaryConfig, String resultDirectory, MyRandom myRandom) {
        this.apsAdversaryConfig = apsAdversaryConfig;
        this.resultDirectory = resultDirectory;
        this.myRandom = myRandom;
    }


    @Override
    public AdversaryRunnable createAdversaryRunner(ApsAdversary adversary, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, Set<PssGroup> pssGroups, List<FinalReport> finalReports) throws PssException {
        String ocTableSaveFileName = DefaultPrintingFileName.getOcTableSaveFileName(resultDirectory);
        OcTableSavable ocTableSaver = new TextFileOcTableSaverInMultipleFiles(ocTableSaveFileName, resultDirectory);
        AdversaryRunnable adversaryRunner = new SimpleApsAdversaryRunner(adversary, finalReports, pssType, ocTableSaver);
        SaveConfig saveConfig = apsAdversaryConfig.getSaveConfig();
        if (saveConfig.shouldSave()) {
            adversaryRunner = withSaver((ShouldSaveConfig) saveConfig);
        }
        PrintConfig printConfig = apsAdversaryConfig.getPrintConfig();
        if (printConfig.shouldPrint()) {
            adversaryRunner = withPrinter(adversaryRunner, (ShouldPrintConfig) printConfig, pssType, fullCycleResults, observedReports, ooiUserGroupMapper, pssVariables);
        }
        adversaryRunner = withDecodabilityCalculator(adversaryRunner, adversary, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper, pssGroups, finalReports);
        return adversaryRunner;
    }

    private AdversaryRunnable withDecodabilityCalculator(AdversaryRunnable adversaryRunner, ApsAdversary adversary, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, Set<PssGroup> pssGroups, List<FinalReport> finalReports) throws PssException {
        DecodabilityCalculationConfig decodabilityCalculationConfig = apsAdversaryConfig.getDecodabilityCalculationConfig();
        Map<DecodabilityCalculationPeriod, DecodabilityPrintConfig> decodabilityPrintConfigMap = decodabilityCalculationConfig.getDecodabilityPrintConfigMap();
        Map<DecodabilityCalculationPeriod, Set<DecodabilityType>> decodabilityMap = decodabilityCalculationConfig.getDecodabilityMap();
        if (decodabilityMap.size() == 0)
            return adversaryRunner;
        Set<DecodabilityType> decodabilityTypes;
        for (DecodabilityCalculationPeriod calculationPeriod : decodabilityMap.keySet()) {
            switch (calculationPeriod) {

                case INTERMEDIATE_POINTS_CALCULATION:
                    decodabilityTypes = decodabilityMap.get(calculationPeriod);
                    adversaryRunner = withIntermediatePointsCalculators(adversaryRunner, adversary, decodabilityTypes, decodabilityPrintConfigMap.get(calculationPeriod), pssType, pssVariables, ooiUserGroupMapper, pssVariables.getValueMap(), fullCycleResults, observedReports, finalReports, pssGroups);
                    break;
                case END_POINT_CALCULATION:
                    decodabilityTypes = decodabilityMap.get(calculationPeriod);
                    adversaryRunner = withEndPointDecodabilityCalculators(pssType, adversaryRunner, decodabilityPrintConfigMap.get(calculationPeriod), decodabilityTypes, adversary, ooiUserGroupMapper, pssVariables.getValueMap(), observedReports, fullCycleResults, pssVariables, pssGroups, finalReports);
                    break;
            }
        }
        return adversaryRunner;
    }

    private AdversaryRunnable withIntermediatePointsCalculators(AdversaryRunnable adversaryRunner, ApsAdversary adversary, Set<DecodabilityType> decodabilityTypes, DecodabilityPrintConfig decodabilityPrintConfig, PssType pssType, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper, ValueMap valueMap, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, List<FinalReport> finalReports, Set<PssGroup> pssGroups) throws PssException {
        if (decodabilityTypes.size() == 0) return adversaryRunner;
        DecodabilityPrintConfig.PrintingSource printingSource = decodabilityPrintConfig.getPrintingSource();
        switch (printingSource) {
            case INHERITED:
                return withInheritedIntermediateCalculators(pssType, adversaryRunner, ooiUserGroupMapper, decodabilityTypes, valueMap, adversary, fullCycleResults, observedReports, pssVariables);
            case SEPARATE:
                return withSeparateIntermediatePointCalculators(adversaryRunner, decodabilityTypes, decodabilityPrintConfig, pssType, adversary, valueMap, ooiUserGroupMapper, pssVariables, finalReports, observedReports, pssGroups, fullCycleResults);
        }

        throw new PssException(String.format("decodability print config %s not matched", printingSource.toString()));
    }

    private AdversaryRunnable withSeparateIntermediatePointCalculators(AdversaryRunnable adversaryRunner, Set<DecodabilityType> decodabilityTypes, DecodabilityPrintConfig decodabilityPrintConfig, PssType pssType, ApsAdversary adversary, ValueMap valueMap, OoiUserGroupMappable ooiUserGroupMapper, PssVariables pssVariables, List<FinalReport> finalReports, List<ObservedReport> observedReports, Set<PssGroup> pssGroups, List<FullCycleResult> fullCycleResults) throws PssException {
        List<AdversaryIntermediatePointsDecodabilityCalculable> decodabilityCalculators = new ArrayList<>();
        AdversaryResultWithIntermediateDecodabilityResultPrintable resultPrinter = getSeparateIntermediatePointFullCycleResultPrinter();

        for (DecodabilityType decodabilityType : decodabilityTypes) {
            switch (decodabilityType) {
                case FULL:
                    IntermediatePointApsAdversaryFullDecodabilityCalculator intermediatePointApsFullDecodabilityCalculator = new IntermediatePointApsAdversaryFullDecodabilityCalculator(pssType, adversary, valueMap, ooiUserGroupMapper, resultDirectory, finalReports);
                    decodabilityCalculators.add(intermediatePointApsFullDecodabilityCalculator);
                    break;
                case LOCATION:
                    AdversaryIntermediatePointsDecodabilityCalculable locationDecodabilityCalculator = generateSeparateIntermediateLocationDecodabilityCalculator(pssType, pssVariables, adversary, valueMap, ooiUserGroupMapper, pssGroups, observedReports, finalReports);
                    decodabilityCalculators.add(locationDecodabilityCalculator);
                    break;

                case PARTIAL:
                    throw new UnsupportedOperationException(String.format("intermediate decodability calculator %s not impl", decodabilityType.toString()));

                case LOCATION_ADD_PRE_DECODING:
                    AdversaryIntermediatePointsDecodabilityCalculable calculator = generateSeparateIntermediateLocationDecodabilityWithPreDecodingCalculator(pssType, pssVariables, adversary, valueMap, ooiUserGroupMapper, pssGroups, observedReports, finalReports);
                    decodabilityCalculators.add(calculator);
                    break;
                default:
                    throw new PssException(String.format("decodability calculation type %s not matched", decodabilityType.toString()));
            }
        }

        adversaryRunner = new AdversaryRunnerWithPrinterWithIntermediatePointCalculator(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper, decodabilityCalculators, resultPrinter, resultDirectory);
        return adversaryRunner;
    }

    private AdversaryIntermediatePointsDecodabilityCalculable generateSeparateIntermediateLocationDecodabilityWithPreDecodingCalculator(PssType pssType, PssVariables pssVariables, ApsAdversary adversary, ValueMap valueMap, OoiUserGroupMappable ooiUserGroupMapper, Set<PssGroup> pssGroups, List<ObservedReport> observedReports, List<FinalReport> finalReports) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {

            case SINGLE:
                throw new UnsupportedOperationException("not impl");
            case MULTI:
                return new MultiIntermediatePointApsAdversaryLocationDecodabilityByAddingOwnCalculator(adversary, valueMap, pssVariables, pssType, pssGroups, ooiUserGroupMapper, observedReports, finalReports, resultDirectory, new TextFileOcTableReaderWithIndex(DefaultPrintingFileName.getOcTableSaveFileName(resultDirectory)));
        }
        throw new PssException(String.format("pss dimension %s not recognized", pssDimensionType.toString()));
    }

    private AdversaryIntermediatePointsDecodabilityCalculable generateSeparateIntermediateLocationDecodabilityCalculator(PssType pssType, PssVariables pssVariables, ApsAdversary adversary, ValueMap valueMap, OoiUserGroupMappable ooiUserGroupMapper, Set<PssGroup> pssGroups, List<ObservedReport> observedReports, List<FinalReport> finalReports) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {
            case SINGLE:
                return new SingleIntermediatePointApsAdversaryLocationDecodabilityCalculator(adversary, valueMap, pssVariables, pssType, pssGroups, ooiUserGroupMapper, observedReports, finalReports, resultDirectory, new TextFileOcTableReaderWithIndex(DefaultPrintingFileName.getOcTableSaveFileName(resultDirectory)));
            case MULTI:
                return new MultiIntermediatePointApsAdversaryLocationDecodabilityCalculator(adversary, valueMap, pssVariables, pssType, pssGroups, ooiUserGroupMapper, observedReports, finalReports, resultDirectory, new TextFileOcTableReaderWithIndex(DefaultPrintingFileName.getOcTableSaveFileName(resultDirectory)));
        }
        throw new PssException(String.format("pss dimension %s not recognized", pssDimensionType.toString()));
    }

    private AdversaryResultWithIntermediateDecodabilityResultPrintable getSeparateIntermediatePointFullCycleResultPrinter() throws PssException {
        return new TextFileAdversaryResultWithIntermediateDecodabilityPrinter(resultDirectory);
    }

    private AdversaryRunnable withInheritedIntermediateCalculators(PssType pssType, AdversaryRunnable adversaryRunner, OoiUserGroupMappable ooiUserGroupMapper, Set<DecodabilityType> decodabilityTypes, ValueMap valueMap, ApsAdversary adversary, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, PssVariables pssVariables) throws PssException {
        List<AdversaryIntermediatePointsDecodabilityCalculable> decodabilityCalculators = new ArrayList<>();
        List<FinalReport> finalReports = new ArrayList<>();
        for (FullCycleResult fullCycleResult : fullCycleResults) {
            finalReports.add(fullCycleResult.getFinalReport());
        }
        AdversaryResultWithIntermediateDecodabilityResultPrintable resultPrinter = getIntermediatePointFullCycleResultPrinter((ShouldPrintConfig) apsAdversaryConfig.getPrintConfig());
        for (DecodabilityType decodabilityType : decodabilityTypes) {
            switch (decodabilityType) {
                case FULL:
                    IntermediatePointApsAdversaryFullDecodabilityCalculator intermediatePointApsFullDecodabilityCalculator = new IntermediatePointApsAdversaryFullDecodabilityCalculator(pssType, adversary, valueMap, ooiUserGroupMapper, resultDirectory, finalReports);
                    decodabilityCalculators.add(intermediatePointApsFullDecodabilityCalculator);
                    break;
                case PARTIAL:
                    throw new UnsupportedOperationException(String.format("intermediate decodability calculator %s not impl", decodabilityType.toString()));

            }
        }

        adversaryRunner = new AdversaryRunnerWithPrinterWithIntermediatePointCalculator(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper, decodabilityCalculators, resultPrinter, resultDirectory);
        return adversaryRunner;
    }

    private AdversaryResultWithIntermediateDecodabilityResultPrintable getIntermediatePointFullCycleResultPrinter(ShouldPrintConfig fullCycleResultPrintConfig) throws PssException {
        String targetFileName = DefaultPrintingFileName.getIntermediateAdversaryDecodabilityResultFileName(fullCycleResultPrintConfig, resultDirectory);
        PrintingTargetType printingTargetType = fullCycleResultPrintConfig.getPrintingTargetType();
        switch (printingTargetType) {
            case HTML:
                return new HtmlAdversaryResultWithIntermediateDecodabilityResultPrinter(targetFileName);
            case CSV:
                throw new UnsupportedOperationException("not impl");
            case TEXT_FILE:
                throw new UnsupportedOperationException("not impl");
            case JSON:
                throw new UnsupportedOperationException("not impl");
        }
        throw new PssException(String.format("printing target type for decodability(with full cycle) %s not matched", printingTargetType.toString()));
    }

    private AdversaryRunnable withEndPointDecodabilityCalculators(PssType pssType, AdversaryRunnable adversaryRunner, DecodabilityPrintConfig decodabilityPrintConfig, Set<DecodabilityType> decodabilityTypes, ApsAdversary apsAdversary, OoiUserGroupMappable ooiUserGroupMapper, ValueMap valueMap, List<ObservedReport> observedReports, List<FullCycleResult> fullCycleResults, PssVariables pssVariables, Set<PssGroup> pssGroups, List<FinalReport> finalReports) throws PssException {
        if (decodabilityTypes.size() == 0) {
            return adversaryRunner;
        }
        EndPointDecodabilityPrintable decodabilityPrinter = getEndPointDecodabilityPrinter(decodabilityPrintConfig);
        List<AdversaryEndPointDecodabilityCalculable> endPointDecodabilityCalculators = new ArrayList<>();
        FullCycleResult lastFullCycleResult = fullCycleResults.get(fullCycleResults.size() - 1);
        FinalReport lastFinalReport = lastFullCycleResult.getFinalReport();
        for (DecodabilityType decodabilityType : decodabilityTypes) {
            switch (decodabilityType) {
                case FULL:
                    AdversaryEndPointDecodabilityCalculable calculator = new EndPointApsAdversaryFullDecodabilityCalculator(apsAdversary, valueMap, pssType, ooiUserGroupMapper, lastFinalReport);
                    endPointDecodabilityCalculators.add(calculator);
                    break;

                case LOCATION_RANDOM:
                    AdversaryEndPointDecodabilityCalculable locationDecodabilityCalculatorRandom = generateLocationDecodabilityCalculatorByRandom(pssType, pssVariables, apsAdversary, valueMap, ooiUserGroupMapper, pssGroups, observedReports, finalReports);
                    endPointDecodabilityCalculators.add(locationDecodabilityCalculatorRandom);


                case LOCATION:
                    AdversaryEndPointDecodabilityCalculable locationDecodabilityCalculatorPrediction = generateLocationDecodabilityCalculatorByPrediction(pssType, pssVariables, apsAdversary, valueMap, ooiUserGroupMapper, pssGroups, observedReports, finalReports);
                    endPointDecodabilityCalculators.add(locationDecodabilityCalculatorPrediction);
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("end point decodability calculator %s not impl", decodabilityType.toString()));
            }
        }
        if (endPointDecodabilityCalculators.size() != 0) {
            adversaryRunner = new AdversaryRunnerWithEndPointCalculator(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper, endPointDecodabilityCalculators, decodabilityPrinter);
        }
        ApsAdversaryReportFilteringMethod apsAdversaryReportFilteringMethod = apsAdversary.getApsAdversaryReportFilteringMethod();
        if (apsAdversaryReportFilteringMethod == ApsAdversaryReportFilteringMethod.TARGET_USER_REPORT || apsAdversaryReportFilteringMethod == ApsAdversaryReportFilteringMethod.TARGET_USER_LIMITED_TIME) {
            adversaryRunner = new AdversaryRunnerWithEndPointTargetDecodabilityCalculator(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper, endPointDecodabilityCalculators, generateTargetDecodabilityPrinter());
        }
        return adversaryRunner;
    }

    private TargetUserLocationDecodabilityPrintable generateTargetDecodabilityPrinter() {
        return new TextFileTargetUserLocationDecodabilityPrinter("target_decodability", SHOULD_APPEND);
    }

    private AdversaryEndPointDecodabilityCalculable generateLocationDecodabilityCalculatorByPrediction(PssType pssType, PssVariables pssVariables, ApsAdversary apsAdversary, ValueMap valueMap, OoiUserGroupMappable ooiUserGroupMapper, Set<PssGroup> pssGroups, List<ObservedReport> observedReports, List<FinalReport> finalReports) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        IdgasDeanonymizer idgasDeanonymizer = (IdgasDeanonymizer) apsAdversary.getDeanonymizer();
        OcTable ocTable = idgasDeanonymizer.getOcTable();
        switch (pssDimensionType) {

            case SINGLE:
                return new SingleEndPointApsAdversaryLocationDecodabilityCalculatorByPrediction(apsAdversary, valueMap, pssVariables, pssType, pssGroups, ooiUserGroupMapper, observedReports, finalReports, ocTable);
            case MULTI:

                return new MultiEndPointApsAdversaryLocationDecodabilityCalculatorByPrediction(apsAdversary, valueMap, pssVariables, pssType, pssGroups, ooiUserGroupMapper, observedReports, finalReports, ocTable);
        }
        throw new PssException(String.format("pss dimension %s not recognized", pssDimensionType.toString()));
    }

    private AdversaryEndPointDecodabilityCalculable generateLocationDecodabilityCalculatorByRandom(PssType pssType, PssVariables pssVariables, ApsAdversary apsAdversary, ValueMap valueMap, OoiUserGroupMappable ooiUserGroupMapper, Set<PssGroup> pssGroups, List<ObservedReport> observedReports, List<FinalReport> finalReports) throws PssException {
        PssType.PssDimensionType pssDimensionType = pssType.getPssDimensionType();
        switch (pssDimensionType) {

            case SINGLE:
                throw new UnsupportedOperationException("not impl");
            case MULTI:
                IdgasDeanonymizer idgasDeanonymizer = (IdgasDeanonymizer) apsAdversary.getDeanonymizer();
                OcTable ocTable = idgasDeanonymizer.getOcTable();
                return new MultiEndPointApsAdversaryLocationDecodabilityCalculatorByRandom(myRandom, apsAdversary, valueMap, pssVariables, pssType, pssGroups, ooiUserGroupMapper, observedReports, finalReports, ocTable);
        }
        throw new PssException(String.format("pss dimension %s not recognized", pssDimensionType.toString()));
    }

    private EndPointDecodabilityPrintable getEndPointDecodabilityPrinter(DecodabilityPrintConfig decodabilityPrintConfig) throws PssException {
        DecodabilityPrintConfig.PrintingSource printingSource = decodabilityPrintConfig.getPrintingSource();
        switch (printingSource) {
            case INHERITED:
                throw new UnsupportedOperationException("not impl");
            case SEPARATE:
                return separateEndPointDecodabilityPrinter((SeperateDecodabilityPrintConfig) decodabilityPrintConfig);
        }
        throw new PssException(String.format("print config %s not found", printingSource.toString()));
    }

    private EndPointDecodabilityPrintable separateEndPointDecodabilityPrinter(SeperateDecodabilityPrintConfig decodabilityPrintConfig) throws PssException {
        ShouldPrintConfig printConfig = decodabilityPrintConfig.getPrintConfig();
        PrintingTargetType printingTargetType = printConfig.getPrintingTargetType();
        String decodabilityFileName = DefaultPrintingFileName.getEndPointAdversaryDecodabilityPrintFileName(resultDirectory);
        String decodedValueFileName = DefaultPrintingFileName.getAdversaryDecodedValueCountFileName(resultDirectory);
        switch (printingTargetType) {
            case HTML:
                throw new UnsupportedOperationException("not impl");
            case CSV:
                throw new UnsupportedOperationException("not impl");
            case TEXT_FILE:
                return new TextFileSeparateEndPointDecodabilityPrinter(decodabilityFileName, decodedValueFileName, decodabilityPrintConfig.getPrintConfig().getAppendMode());
            case JSON:
                throw new UnsupportedOperationException("not impl");
        }
        throw new PssException(String.format("print target type %s not matched", printingTargetType.toString()));
    }

    private SimpleApsAdversaryRunner withSaver(ShouldSaveConfig saveConfig) {
        SavingTargetType savingTargetType = saveConfig.getSavingTargetType();
        switch (savingTargetType) {
            case DB:
                throw new UnsupportedOperationException(String.format("adversary result Saving type %s not implemented", savingTargetType.toString()));
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("adversary result Saving type %s not implemented", savingTargetType.toString()));
            case CSV:
                throw new UnsupportedOperationException(String.format("adversary result Saving type %s not implemented", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("adversary result Saving type %s not implemented", savingTargetType.toString()));
        }
        throw new UnsupportedOperationException(String.format("adversary result Saving type %s not implemented", savingTargetType.toString()));
    }

    private AdversaryRunnable withPrinter(AdversaryRunnable adversaryRunner, ShouldPrintConfig printConfig, PssType pssType, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, PssVariables pssVariables) {
        PrintingTargetType savingTargetType = printConfig.getPrintingTargetType();
        switch (savingTargetType) {
            case TEXT_FILE:
                throw new UnsupportedOperationException(String.format("adversary result printing type %s not implemented", savingTargetType.toString()));
            case HTML:
                String fileName = DefaultPrintingFileName.getAdversaryResultFileName(printConfig, resultDirectory);
                HtmlSimplestApsAdversaryResultPrinter resultPrinter = new HtmlSimplestApsAdversaryResultPrinter(fileName);
                return new AdversaryRunnerWithPrinter(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper, resultPrinter, resultDirectory);
            case CSV:
                throw new UnsupportedOperationException(String.format("adversary result printing type %s not implemented", savingTargetType.toString()));
            case JSON:
                throw new UnsupportedOperationException(String.format("adversary result printing type %s not implemented", savingTargetType.toString()));
        }
        throw new UnsupportedOperationException(String.format("adversary result printing type %s not implemented", savingTargetType.toString()));
    }
}
