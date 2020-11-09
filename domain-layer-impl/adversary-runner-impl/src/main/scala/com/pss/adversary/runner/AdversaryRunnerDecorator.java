package com.pss.adversary.runner;

import com.pss.adversary.runner.runner.AdversaryRunnable;
import pss.data.pss_type.PssType;
import pss.data.pssvariable.PssVariables;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;
import pss.result.full_cycle.FullCycleResult;

import java.util.List;

public abstract class AdversaryRunnerDecorator implements AdversaryRunnable {
    protected final AdversaryRunnable adversaryRunner;
    protected final PssType pssType;
    protected final PssVariables pssVariables;
    protected final List<FullCycleResult> fullCycleResults;
    protected final List<ObservedReport> observedReports;
    protected final OoiUserGroupMappable ooiUserGroupMapper;


    public AdversaryRunnerDecorator(AdversaryRunnable adversaryRunner, PssType pssType, PssVariables pssVariables, List<FullCycleResult> fullCycleResults, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) {
        this.adversaryRunner = adversaryRunner;
        this.pssType = pssType;
        this.pssVariables = pssVariables;
        this.fullCycleResults = fullCycleResults;
        this.observedReports = observedReports;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
    }
}
