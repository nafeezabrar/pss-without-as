package pss.factory.report.generation.adversary;

import com.pss.adversary.Adversary.AdversaryType;
import com.pss.adversary.ApsAdversary.ApsAdversaryReportFilteringMethod;
import com.pss.adversary.ApsAdversaryType;
import com.pss.adversary.ApsAdversaryType.ApsAdversaryUserStrength;
import com.pss.adversary.MultiUserApsAdversaryType;
import com.pss.adversary.RandomUserApsAdversaryType;
import com.pss.adversary.SingleUserApsAdversaryType;
import pss.adversary.report_filtering.*;
import pss.config.InheritedRandomSource;
import pss.config.RandomSource;
import pss.config.adversary.AdversaryConfig;
import pss.config.adversary.ApsAdversaryConfig;
import pss.config.adversary.report_filtering.*;
import pss.config.adversary.report_filtering.ReportFilteringConfig.ReportFilteringMethod;
import pss.config.task.DeanonymizationConfig;
import pss.config.task.DeanonymizationConfig.DeanonymizationMethod;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.SingleOcTable;
import pss.data.pss_type.PssType;
import pss.deanonymization.Deanonymizable;
import pss.deanonymization.idgas.MultiIdgasDeanonymizer;
import pss.deanonymization.idgas.MultiIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.SingleIdgasDeanonymizer;
import pss.deanonymization.idgas.SingleIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.octable.updating.SimpleMultiIdgasIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.octable.updating.SimpleSingleIdgasIdgasDecodingTableUpdater;
import pss.decodability.checking.IdealMultiIdgasDecodabilityChecker;
import pss.decodability.checking.IdealSingleIdgasDecodabilityChecker;
import pss.decodability.checking.MultiDecodabilityChecker;
import pss.decodability.checking.SingleDecodabilityChecker;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.random.RandomProvider;

import java.util.Set;

import static java.lang.String.format;

public class AdversaryFactoryCreator {
    public static AdversaryFactory createAdversaryFactory(PssType pssType, AdversaryConfig adversaryConfig, long runnerSeed) throws PssException {
        AdversaryType adversaryType = adversaryConfig.getAdversaryType();
        switch (adversaryType) {
            case APS_ADVERSARY:
                Deanonymizable deanonymizer = createDeanonymizer(pssType, (ApsAdversaryConfig) adversaryConfig);
                return createApsAdversaryFactory(deanonymizer, (ApsAdversaryConfig) adversaryConfig, runnerSeed);
        }
        throw new PssException(format("Adversary type %s not recognized to factory", adversaryType.toString()));
    }

    private static Deanonymizable createDeanonymizer(PssType pssType, ApsAdversaryConfig adversaryConfig) throws PssException {
        DeanonymizationConfig deanonymizationConfig = adversaryConfig.getDeanonymizationConfig();
        DeanonymizationMethod deanonymizationMethod = deanonymizationConfig.getDeanonymizationMethod();
        switch (deanonymizationMethod) {
            case IDGAS:
                switch (pssType.getPssDimensionType()) {
                    case SINGLE:
                        SingleOcTable singleOcTable = new SingleOcTable();
                        SingleIdgasDecodingTableUpdater ocTableUpdater = new SimpleSingleIdgasIdgasDecodingTableUpdater(singleOcTable);
                        SingleDecodabilityChecker decodabilityChecker = new IdealSingleIdgasDecodabilityChecker(singleOcTable);
                        return new SingleIdgasDeanonymizer(singleOcTable, decodabilityChecker, ocTableUpdater);
                    case MULTI:
                        MultiOcTable multiOcTable = new MultiOcTable(pssType.getDimensionSet().size(), pssType.getDimensionSet());
                        MultiIdgasDecodingTableUpdater multiOcTableUpdater = new SimpleMultiIdgasIdgasDecodingTableUpdater(multiOcTable);
                        MultiDecodabilityChecker multiDecodabilityChecker = new IdealMultiIdgasDecodabilityChecker(multiOcTable);
                        return new MultiIdgasDeanonymizer(multiOcTable, multiDecodabilityChecker, multiOcTableUpdater);
                }
        }
        throw new PssException(format("Deanonymizer type %s not recognized to factory", deanonymizationMethod.toString()));
    }

    private static AdversaryFactory createApsAdversaryFactory(Deanonymizable deanonymizer, ApsAdversaryConfig adversaryConfig, long runnerSeed) throws PssException {
        MyRandom myRandom = RandomProvider.getMyRandom(new InheritedRandomSource(), runnerSeed);
        ReportFilteringConfig reportFilteringConfig = adversaryConfig.getReportFilteringConfig();
        ReportFilter apsAdversaryReportFilter = getApsAdversaryReportFilter(adversaryConfig.getReportFilteringConfig(), runnerSeed);
        return getApsAdversaryFactory(adversaryConfig, apsAdversaryReportFilter, deanonymizer, myRandom);
    }

    private static ReportFilter getApsAdversaryReportFilter(ReportFilteringConfig reportFilteringConfig, long runnerSeed) throws PssException {
        ReportFilter reportFilter;
        ReportFilteringMethod reportFilteringMethod = reportFilteringConfig.getReportFilteringMethod();
        switch (reportFilteringMethod) {
            case ALL_REPORTS:
                reportFilter = new AllReportFilter();
                break;
            case RANDOM_REPORTS:
                reportFilter = getRandomReportFilter((RandomReportFilteringConfig) reportFilteringConfig, runnerSeed);
                break;
            case RANDOM_REPORTS_PERCENTAGE:
                reportFilter = getRandomPercentageReportFilter((RandomReportFilteringWithPercentageConfig) reportFilteringConfig, runnerSeed);
                break;
            case LIMITED_TIME_ALL_REPORTS:
                reportFilter = getLimitedTimeReportFilter((LimitedTimeReportFilteringConfig) reportFilteringConfig);
                break;
            case LIMITED_TIME_ALL_REPORTS_PERCENTAGE:
                reportFilter = getLimitedTimePercentageReportFilter((LimitedTimeReportFilteringWithPercentageConfig) reportFilteringConfig, runnerSeed);
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

    private static ReportFilter getLimitedTimePercentageReportFilter(LimitedTimeReportFilteringWithPercentageConfig reportFilteringConfig, long runnerSeed) throws PssException {
        double percentage = reportFilteringConfig.getPercentage();
        RandomSource randomSource = reportFilteringConfig.getRandomSource();
        MyRandom myRandom = RandomProvider.getMyRandom(reportFilteringConfig.getRandomSource(), runnerSeed);
        return new LimitedTimeWithPercentageReportFilter(myRandom, percentage);
    }

    private static ReportFilter getRandomReportFilter(RandomReportFilteringConfig reportFilteringConfig, long runnerSeed) throws PssException {
        MyRandom myRandom;
        ReportFilter reportFilter;
        RandomReportFilteringConfig randomReportFilteringConfig = reportFilteringConfig;
        myRandom = RandomProvider.getMyRandom(reportFilteringConfig.getRandomSource(), runnerSeed);
        reportFilter = new RandomReportFilter(myRandom, randomReportFilteringConfig.getTotalReports());
        return reportFilter;
    }

    private static ReportFilter getRandomPercentageReportFilter(RandomReportFilteringWithPercentageConfig reportFilteringConfig, long runnerSeed) throws PssException {
        MyRandom myRandom;
        ReportFilter reportFilter;
        myRandom = RandomProvider.getMyRandom(reportFilteringConfig.getRandomSource(), runnerSeed);
        reportFilter = new RandomReportFilterWithPercentage(myRandom, reportFilteringConfig.getPercentage());
        return reportFilter;
    }

    private static ReportFilter getTargetUserLimitedTimeReportFilter(TargetUserLimitedTimeReportFilteringConfig reportFilteringConfig) {
        int startReportId = reportFilteringConfig.getStartReportId();
        int endReportId = reportFilteringConfig.getEndReportId();
        int targetUserId = reportFilteringConfig.getTargetUserId();
        return new TargetUserLimitedTimeReportFilter(startReportId, endReportId, targetUserId);
    }

    private static ReportFilter getLimitedTimeRandomReportFilter(LimitedTimeRandomReportFilteringConfig reportFilteringConfig, long runnerSeed) throws PssException {
        MyRandom myRandom;
        ReportFilter reportFilter;
        LimitedTimeRandomReportFilteringConfig config = reportFilteringConfig;
        myRandom = RandomProvider.getMyRandom(config.getRandomSource(), runnerSeed);
        reportFilter = new LimitedTimeRandomReportFilter(config.getStartReportId(), config.getEndReportId(), myRandom, config.getTotalReports());
        return reportFilter;
    }

    private static AdversaryFactory getApsAdversaryFactory(ApsAdversaryConfig adversaryConfig, ReportFilter apsAdversaryReportFilter, Deanonymizable deanonymizer, MyRandom myRandom) throws PssException {
        ApsAdversaryType apsAdversaryType = adversaryConfig.getApsAdversaryType();
        ApsAdversaryUserStrength apsAdversaryUserStrength = apsAdversaryType.getApsAdversaryUserStrength();
        ReportFilteringConfig reportFilteringConfig = adversaryConfig.getReportFilteringConfig();
        switch (apsAdversaryUserStrength) {
            case DISGUISED_AS_SINGLE_USER:
                SingleUserApsAdversaryType singleUserApsAdversaryType = (SingleUserApsAdversaryType) apsAdversaryType;

                return new SingleUserApsAdversaryFactory(singleUserApsAdversaryType.getUserId(), apsAdversaryReportFilter, deanonymizer, getReportFilteringMethod(reportFilteringConfig));

            case DISGUISED_AS_MULTI_USER:
                MultiUserApsAdversaryType multiUserApsAdversaryType = (MultiUserApsAdversaryType) apsAdversaryType;
                return new MultiUserApsAdversaryFactory(multiUserApsAdversaryType.getUserIds(), apsAdversaryReportFilter, deanonymizer, getReportFilteringMethod(reportFilteringConfig));
            case DISGUISED_AS_RANDOM_USER:
                RandomUserApsAdversaryType randomUserApsAdversaryType = (RandomUserApsAdversaryType) apsAdversaryType;
                return new RandomUserApsAdversaryFactory(myRandom, randomUserApsAdversaryType.getAdversaryUserCount(), apsAdversaryReportFilter, deanonymizer, getReportFilteringMethod(reportFilteringConfig));

        }
        throw new PssException(format("Aps adversary strength %s not matched", apsAdversaryUserStrength.toString()));
    }

    private static ApsAdversaryReportFilteringMethod getReportFilteringMethod(ReportFilteringConfig reportFilteringConfig) throws PssException {
        ReportFilteringMethod reportFilteringMethod = reportFilteringConfig.getReportFilteringMethod();
        switch (reportFilteringMethod) {

            case ALL_REPORTS:
                return ApsAdversaryReportFilteringMethod.ALL_REPORTS;
            case RANDOM_REPORTS:
                return ApsAdversaryReportFilteringMethod.RANDOM_REPORTS;
            case RANDOM_REPORTS_PERCENTAGE:
                return ApsAdversaryReportFilteringMethod.RANDOM_REPORTS_PERCENTAGE;
            case LIMITED_TIME_ALL_REPORTS:
                return ApsAdversaryReportFilteringMethod.LIMITED_TIME_ALL_REPORTS;
            case LIMITED_TIME_ALL_REPORTS_PERCENTAGE:
                return ApsAdversaryReportFilteringMethod.LIMITED_TIME_PERCENTAGE;
            case LIMITED_TIME_RANDOM_REPORTS:
                return ApsAdversaryReportFilteringMethod.LIMITED_TIME_RANDOM_REPORTS;
            case TARGET_USER_REPORT:
                return ApsAdversaryReportFilteringMethod.TARGET_USER_REPORT;
            case TARGET_USER_SET_REPORT:
                return ApsAdversaryReportFilteringMethod.TARGET_USER_SET_REPORT;
            case TARGET_USER_LIMITED_TIME:
                return ApsAdversaryReportFilteringMethod.TARGET_USER_LIMITED_TIME;
        }
        throw new PssException("reportfiltering method not matched");
    }
}
