package pss.runner.cycle.impl.anonymization;

import pss.anonymity.generation.AnonymityGenerable;
import pss.anonymization.Anonymizable;
import pss.anonymizer.selection.AnonymizerSelector;
import pss.data.anonymity.Anonymity;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapper.user.UserMapper;
import pss.report.anonymizable.AnonymizableReport;
import pss.report.generation.AnonymizableReportGenerable;
import pss.report.observed.ObservedReport;
import pss.result.anonymization.AnonymizationResult;
import pss.runner.cycle.anonymization.AnonymizationCycleRunner;

import java.util.ArrayList;
import java.util.List;

public class AnonymizationCycleRunnerImpl implements AnonymizationCycleRunner {
    protected final AnonymizerSelector anonymizerChooser;
    protected final UserMapper userMapper;
    protected final AnonymityGenerable anonymityGenerable;
    protected final AnonymizableReportGenerable anonymizableReportGenerator;

    public AnonymizationCycleRunnerImpl(AnonymizerSelector anonymizerChooser, UserMapper userMapper, AnonymityGenerable anonymityGenerable, AnonymizableReportGenerable anonymizableReportGenerator) {
        this.anonymizerChooser = anonymizerChooser;
        this.userMapper = userMapper;
        this.anonymityGenerable = anonymityGenerable;
        this.anonymizableReportGenerator = anonymizableReportGenerator;
    }

    @Override
    public List<AnonymizationResult> runCycle(List<ObservedReport> observedReports) throws PssException {
        List<AnonymizationResult> anonymizationResults = new ArrayList<>();
        for (ObservedReport observedReport : observedReports) {
            AnonymizationResult anonymizationResult = runCycle(observedReport);
            anonymizationResults.add(anonymizationResult);
        }
        return anonymizationResults;
    }

    @Override
    public AnonymizationResult runCycle(ObservedReport observedReport) throws PssException {
        User user = getUser(observedReport);
        AnonymizableReport anonymizableReport = generateAnonymizableReport(observedReport, user);
        Anonymizable anonymizer = anonymizerChooser.chooseAnonymizer(user);
        return anonymizer.anonymize(anonymizableReport);
    }

    private User getUser(ObservedReport observedReport) throws PssException {
        int userId = observedReport.getReportData().getUserId();
        return userMapper.getUser(userId);
    }

    private AnonymizableReport generateAnonymizableReport(ObservedReport observedReport, User user) {
        Anonymity anonymity = anonymityGenerable.generateAnonymity(user);
        return anonymizableReportGenerator.generateAnonymizableReport(observedReport, anonymity);
    }
}
