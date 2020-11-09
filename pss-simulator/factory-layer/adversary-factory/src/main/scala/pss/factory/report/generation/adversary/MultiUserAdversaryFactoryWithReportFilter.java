package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary;
import pss.adversary.report_filtering.*;
import pss.config.adversary.report_filtering.ReportFilteringConfig;
import pss.config.task.DeanonymizationConfig.DeanonymizationMethod;
import pss.data.pss_type.PssType;
import pss.data.user.User;
import pss.deanonymization.Deanonymizable;
import pss.exception.PssException;
import pss.factory.report.generation.deanonymizer.DeanonymizerFactory;
import pss.library.MyRandom;
import pss.report.finalreport.FinalReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod.*;

public class MultiUserAdversaryFactoryWithReportFilter {
    private static final double limitedPercentage = 2.0;
    private static final double randomPercentage = 2.0;
    private static final double randomPercentageInLimitedTime = 20.0;
    private static final double limitedTimeforRandomPercentage = 10.0;

    public static List<Adversary> generateAdversariesWithReportFilter(PssType pssType, ReportFilteringConfig reportFilteringConfig, Set<Integer> adversaryUserId, DeanonymizationMethod deanonymizationMethod, List<FinalReport> finalReports, MyRandom myRandom, Set<User> userSet, Set<User> users) throws PssException {
        switch (reportFilteringConfig.getReportFilteringMethod()) {
            case ALL_REPORTS:
                return getAllReportAdversaries(pssType, adversaryUserId, deanonymizationMethod, finalReports, users);
            case RANDOM_REPORTS:
                return getRandomReportAdversaries(pssType, adversaryUserId, deanonymizationMethod, finalReports, myRandom, users);
            case LIMITED_TIME_ALL_REPORTS:
                return getLimitedTimeReportAdversaries(pssType, adversaryUserId, deanonymizationMethod, finalReports, users);
            case LIMITED_TIME_RANDOM_REPORTS:
                return getLimitedTimeRandomReportAdversaries(pssType, adversaryUserId, deanonymizationMethod, finalReports, myRandom, users);
            case TARGET_USER_REPORT:
                return getTargetUserReportAdversaries(pssType, adversaryUserId, deanonymizationMethod, finalReports, userSet, users);
            case TARGET_USER_SET_REPORT:
                break;
            case TARGET_USER_LIMITED_TIME:
                return getTargetUserLimitedTimeAdversaries(pssType, adversaryUserId, deanonymizationMethod, finalReports, userSet, users);
        }
        throw new PssException("not matched");
    }

    private static List<Adversary> getTargetUserLimitedTimeAdversaries(PssType pssType, Set<Integer> adversaryUserId, DeanonymizationMethod deanonymizationMethod, List<FinalReport> finalReports, Set<User> userSet, Set<User> users) throws PssException {
        List<Adversary> adversaries = new ArrayList<>();
        for (User user : userSet) {
            int targetUserId = user.getId();
            if (!adversaryUserId.contains(targetUserId)) {
                int totalReportCounts = finalReports.size();
                int leakedReportCount = (int) (totalReportCounts * (limitedPercentage / 100));
                int startReportId = 0;
                int endReportId;
                while (true) {
                    endReportId = startReportId + leakedReportCount - 1;
                    if (endReportId >= finalReports.size()) {
                        break;
                    }
                    Deanonymizable deanonymizer = DeanonymizerFactory.createDeanonymizer(pssType, deanonymizationMethod);
                    MultiUserApsAdversaryFactory adversaryFactory = new MultiUserApsAdversaryFactory(adversaryUserId, new TargetUserLimitedTimeReportFilter(startReportId, endReportId, targetUserId), deanonymizer, TARGET_USER_LIMITED_TIME);
                    Adversary adversary = adversaryFactory.createTargetUserAdversary(targetUserId, finalReports, users);
                    adversaries.add(adversary);
                    startReportId = endReportId + 1;
                }
            }
        }
        return adversaries;
    }

    private static List<Adversary> getLimitedTimeRandomReportAdversaries(PssType pssType, Set<Integer> adversaryUserId, DeanonymizationMethod deanonymizationMethod, List<FinalReport> finalReports, MyRandom myRandom, Set<User> users) throws PssException {
        List<Adversary> adversaries = new ArrayList<>();
        int totalReportCounts = finalReports.size();
        int leakedReportCount = (int) (totalReportCounts * (limitedTimeforRandomPercentage / 100));
        int startReportId = 0;
        int endReportId;
        while (true) {
            endReportId = startReportId + leakedReportCount - 1;
            if (endReportId >= finalReports.size()) {
                break;
            }

            int intervalReports = endReportId - startReportId + 1;
            int randomReportCount = (int) (intervalReports * (randomPercentageInLimitedTime / 100));
            Deanonymizable deanonymizer = DeanonymizerFactory.createDeanonymizer(pssType, deanonymizationMethod);
            MultiUserApsAdversaryFactory adversaryFactory = new MultiUserApsAdversaryFactory(adversaryUserId, new LimitedTimeRandomReportFilter(startReportId, endReportId, myRandom, randomReportCount), deanonymizer, LIMITED_TIME_ALL_REPORTS);
            Adversary adversary = adversaryFactory.createAdversary(finalReports, users);
            adversaries.add(adversary);
            startReportId = endReportId + 1;
        }

        return adversaries;
    }

    private static List<Adversary> getTargetUserReportAdversaries(PssType pssType, Set<Integer> adversaryUserId, DeanonymizationMethod deanonymizationMethod, List<FinalReport> finalReports, Set<User> userSet, Set<User> users) throws PssException {
        List<Adversary> adversaries = new ArrayList<>();
        for (User user : userSet) {
            int targetUserId = user.getId();
            if (!adversaryUserId.contains(targetUserId)) {
                Deanonymizable deanonymizer = DeanonymizerFactory.createDeanonymizer(pssType, deanonymizationMethod);
                MultiUserApsAdversaryFactory adversaryFactory = new MultiUserApsAdversaryFactory(adversaryUserId, new TargetUserReportFilter(targetUserId), deanonymizer, TARGET_USER_REPORT);
                Adversary adversary = adversaryFactory.createTargetUserAdversary(targetUserId, finalReports, users);
                adversaries.add(adversary);
            }
        }
        return adversaries;
    }

    private static List<Adversary> getLimitedTimeReportAdversaries(PssType pssType, Set<Integer> adversaryUserId, DeanonymizationMethod deanonymizationMethod, List<FinalReport> finalReports, Set<User> users) throws PssException {
        List<Adversary> adversaries = new ArrayList<>();
        int totalReportCounts = finalReports.size();
        int leakedReportCount = (int) (totalReportCounts * (limitedPercentage / 100));
        int startReportId = 0;
        int endReportId;
        while (true) {
            endReportId = startReportId + leakedReportCount - 1;
            if (endReportId >= finalReports.size()) {
                break;
            }
            Deanonymizable deanonymizer = DeanonymizerFactory.createDeanonymizer(pssType, deanonymizationMethod);
            MultiUserApsAdversaryFactory adversaryFactory = new MultiUserApsAdversaryFactory(adversaryUserId, new LimitedTimeReportFilter(startReportId, endReportId), deanonymizer, LIMITED_TIME_ALL_REPORTS);
            Adversary adversary = adversaryFactory.createAdversary(finalReports, users);
            adversaries.add(adversary);
            startReportId = endReportId + 1;
        }

        return adversaries;
    }

    private static List<Adversary> getAllReportAdversaries(PssType pssType, Set<Integer> adversaryUserId, DeanonymizationMethod deanonymizationMethod, List<FinalReport> finalReports, Set<User> users) throws PssException {
        List<Adversary> adversaries = new ArrayList<>();
        Deanonymizable deanonymizer = DeanonymizerFactory.createDeanonymizer(pssType, deanonymizationMethod);
        MultiUserApsAdversaryFactory adversaryFactory = new MultiUserApsAdversaryFactory(adversaryUserId, new AllReportFilter(), deanonymizer, ALL_REPORTS);
        Adversary adversary = adversaryFactory.createAdversary(finalReports, users);
        adversaries.add(adversary);
        return adversaries;
    }

    private static List<Adversary> getRandomReportAdversaries(PssType pssType, Set<Integer> adversaryUserId, DeanonymizationMethod deanonymizationMethod, List<FinalReport> finalReports, MyRandom myRandom, Set<User> users) throws PssException {
        List<Adversary> adversaries = new ArrayList<>();
        int totalReportCounts = finalReports.size();
        int leakedReportCount = (int) (totalReportCounts * (randomPercentage / 100));
        Deanonymizable deanonymizer = DeanonymizerFactory.createDeanonymizer(pssType, deanonymizationMethod);
        MultiUserApsAdversaryFactory adversaryFactory = new MultiUserApsAdversaryFactory(adversaryUserId, new RandomReportFilter(myRandom, leakedReportCount), deanonymizer, RANDOM_REPORTS);
        Adversary adversary = adversaryFactory.createAdversary(finalReports, users);
        adversaries.add(adversary);
        return adversaries;
    }
}
