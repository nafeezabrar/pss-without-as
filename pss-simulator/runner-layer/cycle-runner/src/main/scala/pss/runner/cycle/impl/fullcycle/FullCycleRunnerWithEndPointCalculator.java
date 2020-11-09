package pss.runner.cycle.impl.fullcycle;

import pss.data.decodability.DecodabilityType;
import pss.data.pss_type.PssType;
import pss.decodability.DecodabilityResult;
import pss.decodability.EndPointDecodabilityResults;
import pss.decodability.calculation.aps.EndPointDecodabilityCalculable;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.EndPointDecodabilityPrintable;
import pss.runner.cycle.fullcycle.FullCycleRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullCycleRunnerWithEndPointCalculator extends FullCycleRunnerDecorator {

    protected final List<EndPointDecodabilityCalculable> decodabilityCalculators;
    protected final EndPointDecodabilityPrintable decodabilityPrinter;

    public FullCycleRunnerWithEndPointCalculator(FullCycleRunner fullCycleRunner, PssType pssType, OoiUserGroupMappable ooiUserGroupMapper, List<EndPointDecodabilityCalculable> decodabilityCalculators, EndPointDecodabilityPrintable decodabilityPrinter) {
        super(fullCycleRunner, pssType, ooiUserGroupMapper);
        this.decodabilityCalculators = decodabilityCalculators;
        this.decodabilityPrinter = decodabilityPrinter;
    }


    @Override
    public List<FullCycleResult> runCycle(List<ObservedReport> observedReports) throws PssException, IOException, URISyntaxException {
        List<FullCycleResult> fullCycleResults = fullCycleRunner.runCycle(observedReports);
        Map<DecodabilityType, DecodabilityResult> decodabilityResultMap = new HashMap<>();
        for (EndPointDecodabilityCalculable decodabilityCalculator : decodabilityCalculators) {
            DecodabilityResult decodabilityResult = decodabilityCalculator.calculateDecodability();
            decodabilityResultMap.put(decodabilityCalculator.getDecodabilityType(), decodabilityResult);
        }
        EndPointDecodabilityResults decodabilityResults = new EndPointDecodabilityResults(decodabilityResultMap);
        decodabilityPrinter.printDecodability(decodabilityResults);
        return fullCycleResults;
    }
}
