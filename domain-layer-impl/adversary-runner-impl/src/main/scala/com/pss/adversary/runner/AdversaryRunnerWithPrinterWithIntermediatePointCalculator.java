package com.pss.adversary.runner;


import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.data.decodability.DecodabilityType;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.decodability.DecodabilityResult;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.decodability.calculation.aps.AdversaryIntermediatePointsDecodabilityCalculable;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.adversary.AdversaryResultWithSingleOcTable;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.AdversaryResultWithIntermediateDecodabilityResultPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class AdversaryRunnerWithPrinterWithIntermediatePointCalculator extends AdversaryRunnerDecorator {
    protected final List<AdversaryIntermediatePointsDecodabilityCalculable> decodabilityCalculators;
    protected final AdversaryResultWithIntermediateDecodabilityResultPrintable decodabilityPrinter;
    protected final String resultDirectory;

    public AdversaryRunnerWithPrinterWithIntermediatePointCalculator(AdversaryRunnable adversaryRunner, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, List<AdversaryIntermediatePointsDecodabilityCalculable> decodabilityCalculators, AdversaryResultWithIntermediateDecodabilityResultPrintable decodabilityPrinter, String resultDirectory) {
        super(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper);
        this.decodabilityCalculators = decodabilityCalculators;
        this.decodabilityPrinter = decodabilityPrinter;
        this.resultDirectory = resultDirectory;
    }


    @Override
    public AdversaryResult runAdversary() throws IOException, PssException, URISyntaxException, ClassNotFoundException {
        AdversaryResult adversaryResult = adversaryRunner.runAdversary();
        Set<DecodabilityType> decodabilityTypes = getDecodabilityTypes();
        IntermediatePointDecodabilityResults decodabilityResult = getDecodabilityResultMaps(adversaryResult, decodabilityTypes);

        decodabilityPrinter.printAdversaryResultWithIntermediateDecodability(fullCycleResults, pssType, pssVariables, ooiUserGroupMapper, observedReports, (AdversaryResultWithSingleOcTable) adversaryResult, decodabilityResult, resultDirectory);
        return adversaryResult;
    }

    private Set<DecodabilityType> getDecodabilityTypes() {
        Set<DecodabilityType> decodabilityTypes = new HashSet<>();
        for (AdversaryIntermediatePointsDecodabilityCalculable calculator : decodabilityCalculators) {
            decodabilityTypes.add(calculator.getDecodabilityType());
        }
        return decodabilityTypes;
    }

    private IntermediatePointDecodabilityResults getDecodabilityResultMaps(AdversaryResult adversaryResult, Set<DecodabilityType> decodabilityTypes) throws PssException, IOException, ClassNotFoundException {
        Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> decodabilityResultMaps = new HashMap<>();
        for (FullCycleResult fullCycleResult : fullCycleResults) {
            Map<DecodabilityType, DecodabilityResult> decodabilityResultMap = new HashMap<>();
            for (AdversaryIntermediatePointsDecodabilityCalculable decodabilityCalculator : decodabilityCalculators) {
                FinalReport finalReport = fullCycleResult.getFinalReport();
                DecodabilityResult decodabilityResult = decodabilityCalculator.calculateDecodability(adversaryResult, finalReport);
                decodabilityResultMap.put(decodabilityCalculator.getDecodabilityType(), decodabilityResult);
            }
            decodabilityResultMaps.put(fullCycleResult, decodabilityResultMap);
        }
        return new IntermediatePointDecodabilityResults(decodabilityResultMaps, decodabilityTypes);
    }
}
