package pss.runner.cycle.impl.fullcycle;

import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymization.Anonymizable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.anonymity.Anonymity;
import pss.data.ooi.local.collection.LocalOoiCollection;
import pss.data.ooi.local.combination.LocalOoiCombination;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.data.valuemap.Value;
import pss.deanonymization.Deanonymizable;
import pss.deanonymizer.selection.DeanonymizerSelector;
import pss.exception.PssException;
import pss.mapper.user.UserMapper;
import pss.report.anonymizable.AnonymizableReport;
import pss.report.finalreport.FinalReport;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.generation.FinalReportInheritedGenerable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.result.deanonymization.DeanonymizationResult;
import pss.result.full_cycle.FullCycleResult;
import pss.runner.cycle.fullcycle.FullCycleRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FullCycleRunnerImpl implements FullCycleRunner {
    protected final Set<PssGroup> pssGroups;
    protected final AnonymizerSelector anonymizerSelector;
    protected final DeanonymizerSelector deanonymizerSelector;
    protected final UserMapper userMapper;
    protected final AnonymityGenerable anonymityGenerable;
    protected final AnonymizableReportGenerable anonymizableReportGenerator;
    protected final FinalReportInheritedGenerable finalReportGenerable;

    public FullCycleRunnerImpl(Set<PssGroup> pssGroups, AnonymizerSelector anonymizerSelector, DeanonymizerSelector deanonymizerSelector, UserMapper userMapper, AnonymityGenerable anonymityGenerable, AnonymizableReportGenerable anonymizableReportGenerator, FinalReportInheritedGenerable finalReportGenerator) {
        this.pssGroups = pssGroups;
        this.anonymizerSelector = anonymizerSelector;
        this.deanonymizerSelector = deanonymizerSelector;
        this.userMapper = userMapper;
        this.anonymityGenerable = anonymityGenerable;
        this.anonymizableReportGenerator = anonymizableReportGenerator;
        this.finalReportGenerable = finalReportGenerator;
    }

    @Override
    public List<FullCycleResult> runCycle(List<ObservedReport> observedReports) throws PssException {
        List<FullCycleResult> fullCycleResults = new ArrayList<>();
        for (ObservedReport observedReport : observedReports) {
            FullCycleResult fullCycleResult = runCycle(observedReport);
            fullCycleResults.add(fullCycleResult);
        }
        return fullCycleResults;
    }

    @Override
    public FullCycleResult runCycle(ObservedReport observedReport) throws PssException {
        User user = getUser(observedReport);
        AnonymizableReport anonymizableReport = generateAnonymizableReport(observedReport, user);
        Anonymizable anonymizer = anonymizerSelector.chooseAnonymizer(user);
        AnonymizationResult anonymizationResult = anonymizer.anonymize(anonymizableReport);
        Anonymity anonymity = anonymizableReport.getReportData().getAnonymity();
        FinalReport finalReport = generateFinalReport(observedReport.getId(), observedReport.getReportData().getLocalOoiCombination(), observedReport.getValue(), anonymizationResult, user, anonymity);
        anonymizer.updateIfRequired(finalReport);
        DeanonymizationResult deanonymizationResult = getDeanonymizationResult(finalReport, user);
        int totalDecoded = getTotalDecoded();
        return new FullCycleResult(anonymizationResult, deanonymizationResult, finalReport, totalDecoded);
    }

    private int getTotalDecoded() throws PssException {
        int totalDecoded = 0;
        for (PssGroup pssGroup : pssGroups) {
            totalDecoded += getDecodedValuesForGroup(pssGroup);
        }
        return totalDecoded;
    }

    private int getDecodedValuesForGroup(PssGroup pssGroup) throws PssException {
        Deanonymizable deanonymizer = deanonymizerSelector.selectDeanonymizer(pssGroup);
        return deanonymizer.getDecodedValueMap().size();
    }

    private User getUser(ObservedReport observedReport) throws PssException {
        int userId = observedReport.getReportData().getUserId();
        return userMapper.getUser(userId);
    }

    private AnonymizableReport generateAnonymizableReport(ObservedReport observedReport, User user) {
        Anonymity anonymity = (Anonymity) anonymityGenerable.generateAnonymity(user);
//        System.out.println(anonymity);
        return anonymizableReportGenerator.generateAnonymizableReport(observedReport, anonymity);
    }

    private FinalReport generateFinalReport(int reportId, LocalOoiCombination reportedOoiCombination, Value value, AnonymizationResult anonymizationResult, User user, Anonymity anonymity) throws PssException {
        Set<LocalOoiCollection> localOoiCollections = new HashSet<>();
        for (PssGroup pssGroup : pssGroups) {
            localOoiCollections.add(pssGroup.getLocalOoiCollection());
        }
        return finalReportGenerable.generateFinalReport(reportId, localOoiCollections, reportedOoiCombination, anonymizationResult.getAnonymizedLocalOoiSet(), user, value, anonymity);
    }

    private DeanonymizationResult getDeanonymizationResult(FinalReport finalReport, User user) throws PssException {
        Deanonymizable deanonymizer = deanonymizerSelector.selectDeanonymizer(user);
        return deanonymizer.deanonymize(finalReport);
    }
}
