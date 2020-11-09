package pss.runner.cycle.impl.fullcycle;


import pss.data.decodability.DecodabilityType;
import pss.data.pss_type.PssType;
import pss.decodability.DecodabilityResult;
import pss.decodability.IntermediatePointDecodabilityResults;
import pss.decodability.calculation.aps.IntermediatePointsDecodabilityCalculable;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.FullCycleResultWithIntermediateDecodabilityResultPrintable;
import pss.runner.cycle.fullcycle.FullCycleRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class FullCycleRunnerWithPrinterWithIntermediatePointsCalculator extends FullCycleRunnerDecorator {

    protected final List<IntermediatePointsDecodabilityCalculable> decodabilityCalculators;
    protected final FullCycleResultWithIntermediateDecodabilityResultPrintable resultPrinter;

    public FullCycleRunnerWithPrinterWithIntermediatePointsCalculator(FullCycleRunner fullCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<IntermediatePointsDecodabilityCalculable> decodabilityCalculators, FullCycleResultWithIntermediateDecodabilityResultPrintable resultPrinter) {
        super(fullCycleRunner, pssType, ooiUserGroupMapper);
        this.decodabilityCalculators = decodabilityCalculators;
        this.resultPrinter = resultPrinter;
    }


    @Override
    public List<FullCycleResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException, URISyntaxException {
        List<FullCycleResult> fullCycleResults = fullCycleRunner.runCycle(observedReports);
        Set<DecodabilityType> decodabilityTypes = getDecodabilityTypes();
        IntermediatePointDecodabilityResults decodabilityResult = getDecodabilityResultMaps(fullCycleResults, decodabilityTypes);

        resultPrinter.printFullCycleResultWithIntermediateDecodability(fullCycleResults, pssType, ooiUserGroupMapper, observedReports, decodabilityResult);
        return fullCycleResults;
    }

    private Set<DecodabilityType> getDecodabilityTypes() {
        Set<DecodabilityType> decodabilityTypes = new HashSet<>();
        for (IntermediatePointsDecodabilityCalculable calculator : decodabilityCalculators) {
            decodabilityTypes.add(calculator.getDecodabilityType());
        }
        return decodabilityTypes;
    }

    private IntermediatePointDecodabilityResults getDecodabilityResultMaps(List<FullCycleResult> fullCycleResults, Set<DecodabilityType> decodabilityTypes) throws PssException {
        Map<FullCycleResult, Map<DecodabilityType, DecodabilityResult>> decodabilityResultMaps = new HashMap<>();
        for (FullCycleResult fullCycleResult : fullCycleResults) {
            Map<DecodabilityType, DecodabilityResult> decodabilityResultMap = new HashMap<>();
            for (IntermediatePointsDecodabilityCalculable decodabilityCalculator : decodabilityCalculators) {
                DecodabilityResult decodabilityResult = decodabilityCalculator.calculateDecodability(fullCycleResult.getDeanonymizationResult());
                decodabilityResultMap.put(decodabilityCalculator.getDecodabilityType(), decodabilityResult);
            }
            decodabilityResultMaps.put(fullCycleResult, decodabilityResultMap);
        }
        return new IntermediatePointDecodabilityResults(decodabilityResultMaps, decodabilityTypes);
    }
}
