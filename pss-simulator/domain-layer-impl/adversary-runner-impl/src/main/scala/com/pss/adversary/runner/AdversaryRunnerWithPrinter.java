package com.pss.adversary.runner;


import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.adversary.AdversaryResult;
import pss.result.full_cycle.FullCycleResult;
import pss.result.presentable.printing.AdversaryResultPrintable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class AdversaryRunnerWithPrinter extends AdversaryRunnerDecorator {
    protected final AdversaryResultPrintable adversaryResultPrinter;
    protected final String resultDirectory;

    public AdversaryRunnerWithPrinter(AdversaryRunnable adversaryRunner, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper, AdversaryResultPrintable adversaryResultPrinter, String resultDirectory) {
        super(adversaryRunner, pssType, pssVariables, fullCycleResults, observedReports, ooiUserGroupMapper);
        this.adversaryResultPrinter = adversaryResultPrinter;
        this.resultDirectory = resultDirectory;
    }


    @Override
    public AdversaryResult runAdversary() throws IOException, PssException, URISyntaxException, ClassNotFoundException {
        AdversaryResult adversaryResult = adversaryRunner.runAdversary();
        adversaryResultPrinter.printAdversaryResult(adversaryResult, fullCycleResults, pssType, ooiUserGroupMapper, observedReports, resultDirectory);
        return adversaryResult;
    }
}
