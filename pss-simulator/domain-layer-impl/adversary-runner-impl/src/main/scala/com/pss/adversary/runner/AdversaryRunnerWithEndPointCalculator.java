package com.pss.adversary.runner;


import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.data.decodability.DecodabilityType;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.decodability.DecodabilityResult;
import pss.decodability.EndPointDecodabilityResults;
import pss.decodability.calculation.aps.AdversaryEndPointDecodabilityCalculable;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.EndPointDecodabilityPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdversaryRunnerWithEndPointCalculator extends AdversaryRunnerDecorator {
    protected final List<AdversaryEndPointDecodabilityCalculable> decodabilityCalculators;
    protected final EndPointDecodabilityPrintable decodabilityPrinter;


    public AdversaryRunnerWithEndPointCalculator(AdversaryRunnable adversaryRunner, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, List<AdversaryEndPointDecodabilityCalculable> decodabilityCalculators, EndPointDecodabilityPrintable decodabilityPrinter) {
        super(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper);
        this.decodabilityCalculators = decodabilityCalculators;
        this.decodabilityPrinter = decodabilityPrinter;
    }


    @Override
    public AdversaryResult runAdversary() throws IOException, PssException, URISyntaxException, ClassNotFoundException {
        AdversaryResult adversaryResult = adversaryRunner.runAdversary();
        Map<DecodabilityType, DecodabilityResult> decodabilityResultMap = new HashMap<>();
        for (AdversaryEndPointDecodabilityCalculable decodabilityCalculator : decodabilityCalculators) {
            DecodabilityResult decodabilityResult = decodabilityCalculator.calculateDecodability(adversaryResult);
            decodabilityResultMap.put(decodabilityCalculator.getDecodabilityType(), decodabilityResult);
        }
        decodabilityPrinter.printDecodability(new EndPointDecodabilityResults(decodabilityResultMap));
        return adversaryResult;
    }
}
