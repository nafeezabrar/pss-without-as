package pss.factory.report.generation.adversary;

import pss.adversary.report_filtering.*;
import pss.config.adversary.MultipleApsAdversaryConfig;
import pss.config.adversary.report_filtering.*;
import pss.config.task.DeanonymizationConfig;
import pss.data.pss_type.PssType;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.random.RandomProvider;

import java.util.Set;

import static java.lang.String.format;
import static pss.config.adversary.MultipleApsAdversaryConfig.ApsAdversaryGenerationType;

public class MultipleApsAdversaryFactoryCreator {
    public static MultipleAdversaryFactory createAdversaryFactory(PssType pssType, MultipleApsAdversaryConfig apsAdversaryConfig, long runnerSeed) throws PssException {
        ApsAdversaryGenerationType apsAdversaryGenerationType = apsAdversaryConfig.getApsAdversaryGenerationType();
        ReportFilter reportFilter = getApsAdversaryReportFilter(apsAdversaryConfig.getReportFilteringConfig(), runnerSeed);
        MyRandom myRandom = new MyRandom(runnerSeed);
        DeanonymizationConfig.DeanonymizationMethod deanonymizationMethod = apsAdversaryConfig.getDeanonymizationConfig().getDeanonymizationMethod();
        switch (apsAdversaryGenerationType) {

            case SIMPLE_SET_1:
                return new SimpleSetOneApsAdversaryFactory(reportFilter, deanonymizationMethod, myRandom);
            case SIMPLE_SET_2:
                return new SimpleSetTwoApsAdversaryFactory(reportFilter, deanonymizationMethod, myRandom);
            case SIMPLE_SET_3:
                return new SimpleSetThreeApsAdversaryFactory(reportFilter, deanonymizationMethod, myRandom);
        }
        throw new PssException(String.format("adversaryGenerationType %s not matched", apsAdversaryGenerationType.toString()));
    }


    private static ReportFilter getApsAdversaryReportFilter(ReportFilteringConfig reportFilteringConfig, long runnerSeed) throws PssException {
        ReportFilter reportFilter;
        ReportFilteringConfig.ReportFilteringMethod reportFilteringMethod = reportFilteringConfig.getReportFilteringMethod();
        switch (reportFilteringMethod) {
            case ALL_REPORTS:
                reportFilter = new AllReportFilter();
                break;
            case RANDOM_REPORTS:
                reportFilter = getRandomReportFilter((RandomReportFilteringConfig) reportFilteringConfig, runnerSeed);
                break;
            case RANDOM_REPORTS_PERCENTAGE:
                reportFilter = getRandomReportPercentageFilter((RandomReportFilteringWithPercentageConfig) reportFilteringConfig, runnerSeed);
                break;
            case LIMITED_TIME_ALL_REPORTS:
                reportFilter = getLimitedTimeReportFilter((LimitedTimeReportFilteringConfig) reportFilteringConfig);
                break;
            case LIMITED_TIME_ALL_REPORTS_PERCENTAGE:
                reportFilter = getLimitedTimePercentageFilter((LimitedTimeReportFilteringWithPercentageConfig) reportFilteringConfig, runnerSeed);
                break;
            case LIMITED_TIME_RANDOM_REPORTS:
                reportFilter = getLimitedTimeRandomReportFilter((LimitedTimeRandomReportFilteringConfig) reportFilteringConfig, runnerSeed);
                break;
            case TARGET_USER_REPORT:
                reportFilter = getTargetUserReportFilter((TargetUserReportFilteringConfig) reportFilteringConfig);
                break;
            case TARGET_USER_SET_REPORT:
                reportFilter = getTargetUserSetReportFilter((TargetUserSetReportFilteringConfig) reportFilteringConfig);
                break;
            case TARGET_USER_LIMITED_TIME:
                reportFilter = getTargetUserLimitedTimeReportFilter((TargetUserLimitedTimeReportFilteringConfig) reportFilteringConfig);
                break;
            default:
                throw new PssException(format("Report filtering type %s not recognized to factory", reportFilteringMethod.toString()));
        }
        return reportFilter;
    }

    private static ReportFilter getLimitedTimePercentageFilter(LimitedTimeReportFilteringWithPercentageConfig reportFilteringConfig, long runnerSeed) throws PssException {
        MyRandom myRandom;
        LimitedTimeReportFilteringWithPercentageConfig config = reportFilteringConfig;
        myRandom = RandomProvider.getMyRandom(config.getRandomSource(), runnerSeed);
        double percentage = reportFilteringConfig.getPercentage();
        return new LimitedTimeWithPercentageReportFilter(myRandom, percentage);
    }

    private static ReportFilter getRandomReportPercentageFilter(RandomReportFilteringWithPercentageConfig reportFilteringConfig, long runnerSeed) throws PssException {
        MyRandom myRandom;
        myRandom = RandomProvider.getMyRandom(reportFilteringConfig.getRandomSource(), runnerSeed);
        double percentage = reportFilteringConfig.getPercentage();
        return new RandomReportFilterWithPercentage(myRandom, percentage);
    }

    private static ReportFilter getTargetUserLimitedTimeReportFilter(TargetUserLimitedTimeReportFilteringConfig reportFilteringConfig) {
        int startReportId = reportFilteringConfig.getStartReportId();
        int endReportId = reportFilteringConfig.getEndReportId();
        int userId = reportFilteringConfig.getTargetUserId();
        return new TargetUserLimitedTimeReportFilter(startReportId, endReportId, userId);
    }


    private static ReportFilter getTargetUserSetReportFilter(TargetUserSetReportFilteringConfig reportFilteringConfig) {
        Set<Integer> userIds = reportFilteringConfig.getUserIds();
        return new TargetUserSetReportFilter(userIds);
    }

    private static ReportFilter getTargetUserReportFilter(TargetUserReportFilteringConfig reportFilteringConfig) {
        int userId = reportFilteringConfig.getTargetUserId();
        return new TargetUserReportFilter(userId);
    }

    private static ReportFilter getLimitedTimeReportFilter(LimitedTimeReportFilteringConfig reportFilteringConfig) {
        int startReportId = reportFilteringConfig.getStartReportId();
        int endReportId = reportFilteringConfig.getEndReportId();
        return new LimitedTimeReportFilter(startReportId, endReportId);
    }

    private static ReportFilter getRandomReportFilter(RandomReportFilteringConfig reportFilteringConfig, long runnerSeed) throws PssException {
        MyRandom myRandom;
        ReportFilter reportFilter;
        RandomReportFilteringConfig randomReportFilteringConfig = reportFilteringConfig;
        myRandom = RandomProvider.getMyRandom(reportFilteringConfig.getRandomSource(), runnerSeed);
        reportFilter = new RandomReportFilter(myRandom, randomReportFilteringConfig.getTotalReports());
        return reportFilter;
    }

    private static ReportFilter getLimitedTimeRandomReportFilter(LimitedTimeRandomReportFilteringConfig reportFilteringConfig, long runnerSeed) throws PssException {
        MyRandom myRandom;
        ReportFilter reportFilter;
        LimitedTimeRandomReportFilteringConfig config = reportFilteringConfig;
        myRandom = RandomProvider.getMyRandom(config.getRandomSource(), runnerSeed);
        reportFilter = new LimitedTimeRandomReportFilter(config.getStartReportId(), config.getEndReportId(), myRandom, config.getTotalReports());
        return reportFilter;
    }
}
