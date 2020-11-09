package com.pss.adversary.runner;


import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.data.decodability.DecodabilityType;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.decodability.DecodabilityResult;
import pss.decodability.LocationDecodabilityResult;
import pss.decodability.SingleTargetUserLocationDecodabilityResult;
import pss.decodability.TargetUserLocationDecodabilityResult;
import pss.decodability.calculation.aps.AdversaryEndPointDecodabilityCalculable;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.TargetUserLocationDecodabilityPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pss.decodability.TargetUserLocationDecodabilityResult.TargetUserInfo.NO_TARGET_USER;

public class AdversaryRunnerWithEndPointTargetDecodabilityCalculator extends AdversaryRunnerDecorator {
    protected final List<AdversaryEndPointDecodabilityCalculable> decodabilityCalculators;
    protected final TargetUserLocationDecodabilityPrintable targetUserLocationDecodabilityPrinter;

    public AdversaryRunnerWithEndPointTargetDecodabilityCalculator(AdversaryRunnable adversaryRunner, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, List<AdversaryEndPointDecodabilityCalculable> decodabilityCalculators, TargetUserLocationDecodabilityPrintable targetUserLocationDecodabilityPrinter) {
        super(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper);
        this.decodabilityCalculators = decodabilityCalculators;
        this.targetUserLocationDecodabilityPrinter = targetUserLocationDecodabilityPrinter;
    }


    @Override
    public AdversaryResult runAdversary() throws IOException, PssException, URISyntaxException, ClassNotFoundException {
        AdversaryResult adversaryResult = adversaryRunner.runAdversary();
        Map<DecodabilityType, DecodabilityResult> decodabilityResultMap = new HashMap<>();
        for (AdversaryEndPointDecodabilityCalculable decodabilityCalculator : decodabilityCalculators) {
            DecodabilityResult decodabilityResult = decodabilityCalculator.calculateDecodability(adversaryResult);
            decodabilityResultMap.put(decodabilityCalculator.getDecodabilityType(), decodabilityResult);
            if (decodabilityCalculator.getDecodabilityType() == DecodabilityType.LOCATION) {
                LocationDecodabilityResult locationDecodabilityResult = (LocationDecodabilityResult) decodabilityResult;
                TargetUserLocationDecodabilityResult targetUserLocationDecodabilityResult = locationDecodabilityResult.getTargetUserLocationDecodabilityResult();
                if (targetUserLocationDecodabilityResult.getTargetUserInfo() != NO_TARGET_USER) {
                    SingleTargetUserLocationDecodabilityResult result = (SingleTargetUserLocationDecodabilityResult) targetUserLocationDecodabilityResult;
                    targetUserLocationDecodabilityPrinter.printTargetUserLocationDecodability(result.getTargetUserLocationDecodability());
                }
            }
        }
        return adversaryResult;
    }
}
