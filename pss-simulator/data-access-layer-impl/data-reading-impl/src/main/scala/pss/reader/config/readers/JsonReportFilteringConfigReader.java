package pss.reader.config.readers;

import org.json.simple.JSONObject;
import pss.config.RandomSource;
import pss.config.StringToConfig;
import pss.config.adversary.report_filtering.*;
import pss.exception.InvalidConfigException;
import pss.exception.ReaderException;
import pss.reader.json.JsonFileReader;

import java.util.HashSet;
import java.util.Set;

import static pss.config.ConfigKeyString.Adversary.ReportFiltering.*;

public class JsonReportFilteringConfigReader {
    public static ReportFilteringConfig readReportFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        String reportFilteringTypeString = jsonFileReader.getString(jsonObject, reportFilteringType);
        ReportFilteringConfig.ReportFilteringMethod reportFilteringType = StringToConfig.getReportFilteringType(reportFilteringTypeString);
        switch (reportFilteringType) {
            case ALL_REPORTS:
                return readWholeTimeAllReportFilteringConfig(jsonObject, jsonFileReader);
            case RANDOM_REPORTS:
                return readWholeTimeRandomReportFilteringConfig(jsonObject, jsonFileReader);
            case RANDOM_REPORTS_PERCENTAGE:
                return readRandomReportWithPercentageFilteringConfig(jsonObject, jsonFileReader);
            case LIMITED_TIME_ALL_REPORTS:
                return readLimitedTimeAllReportFilteringConfig(jsonObject, jsonFileReader);
            case LIMITED_TIME_ALL_REPORTS_PERCENTAGE:
                return readLimitedTimeWithPercentageFilteringConfig(jsonObject, jsonFileReader);
            case LIMITED_TIME_RANDOM_REPORTS:
                return readLimitedTimeRandomReportFilteringConfig(jsonObject, jsonFileReader);
            case TARGET_USER_REPORT:
                return readWholeTimeTargetUserFilteringConfig(jsonObject, jsonFileReader);
            case TARGET_USER_SET_REPORT:
                return readWholeTimeTargetUserSetFilteringConfig(jsonObject, jsonFileReader);
            case TARGET_USER_LIMITED_TIME:
                return readLimitedTimeTargetUserFilteringConfig(jsonObject, jsonFileReader);
        }
        throw new InvalidConfigException(String.format("Report filtering type %s not recognized", reportFilteringType.toString()));
    }

    private static ReportFilteringConfig readRandomReportWithPercentageFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {

        RandomSource randomSource = JsonRandomSourceReader.readRandomSource(jsonObject, jsonFileReader);
        if (jsonFileReader.containsKey(reportCountKey)) {
            int reportCount = jsonFileReader.getInt(jsonObject, reportCountKey);
            return new RandomReportFilteringConfig(randomSource, reportCount);
        } else {
            int percentage = jsonFileReader.getInt(jsonObject, reportPercentageKey);
            return new RandomReportFilteringWithPercentageConfig(randomSource, percentage);
        }
    }

    private static ReportFilteringConfig readWholeTimeAllReportFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) {
        return new WholeTimeAllReportFilteringConfig();
    }

    private static ReportFilteringConfig readLimitedTimeAllReportFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        int startReportId = jsonFileReader.getInt(jsonObject, startReportIdKey);
        int endReportId = jsonFileReader.getInt(jsonObject, endReportIdKey);
        return new LimitedTimeReportFilteringConfig(startReportId, endReportId);
    }

    private static ReportFilteringConfig readLimitedTimeRandomReportFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException, InvalidConfigException {
        int startReportId = jsonFileReader.getInt(jsonObject, startReportIdKey);
        int endReportId = jsonFileReader.getInt(jsonObject, endReportIdKey);
        RandomSource randomSource = JsonRandomSourceReader.readRandomSource(jsonObject, jsonFileReader);
        int reportCount = jsonFileReader.getInt(jsonObject, reportCountKey);
        int reportsWithingTime = endReportId - startReportId + 1;
        if (endReportId < startReportId)
            throw new InvalidConfigException(String.format("report range %d to %d is invalid", startReportId, endReportId));
        if (reportCount > reportsWithingTime)
            throw new InvalidConfigException(String.format("Report count %d is greater than reports within time range %d", reportCount, reportsWithingTime));
        return new LimitedTimeRandomReportFilteringConfig(startReportId, endReportId, randomSource, reportCount);
    }

    private static ReportFilteringConfig readLimitedTimeWithPercentageFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        double percentage = jsonFileReader.getInt(jsonObject, reportPercentageKey);
        RandomSource randomSource = JsonRandomSourceReader.readRandomSource(jsonObject, jsonFileReader);
        return new LimitedTimeReportFilteringWithPercentageConfig(randomSource, percentage);
    }

    private static ReportFilteringConfig readWholeTimeTargetUserFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        int targetUserId = jsonFileReader.getInt(jsonObject, targetUserIdKey);
        return new TargetUserReportFilteringConfig(targetUserId);
    }

    private static ReportFilteringConfig readWholeTimeTargetUserSetFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        int[] targetUserIds = jsonFileReader.getIntArray(jsonObject, targetUserIdSetKey);
        Set<Integer> targetUserIdSet = new HashSet<>();
        for (int i = 0; i < targetUserIds.length; i++) {
            targetUserIdSet.add(targetUserIds[i]);
        }
        return new TargetUserSetReportFilteringConfig(targetUserIdSet);
    }

    private static ReportFilteringConfig readWholeTimeRandomReportFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        RandomSource randomSource = JsonRandomSourceReader.readRandomSource(jsonObject, jsonFileReader);

        int reportCount = jsonFileReader.getInt(jsonObject, reportCountKey);
        return new RandomReportFilteringConfig(randomSource, reportCount);

    }

    private static ReportFilteringConfig readLimitedTimeTargetUserFilteringConfig(JSONObject jsonObject, JsonFileReader jsonFileReader) throws ReaderException {
        int startReportId = jsonFileReader.getInt(jsonObject, startReportIdKey);
        int endReportId = jsonFileReader.getInt(jsonObject, endReportIdKey);
        int targetUserId = jsonFileReader.getInt(jsonObject, targetUserIdKey);
        return new TargetUserLimitedTimeReportFilteringConfig(startReportId, endReportId, targetUserId);
    }
}
