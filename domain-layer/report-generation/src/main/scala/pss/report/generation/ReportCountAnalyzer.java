package pss.report.generation;

import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.observed.ObservedReport;

import java.util.*;

public class ReportCountAnalyzer {
    public static Map<User, Integer> generateObservedReportCountMap(Set<User> users, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Map<User, Integer> observedReportCountMap = new HashMap<>();
        for (ObservedReport observedReport : observedReports) {
            int userId = observedReport.getReportData().getUserId();
            User user = ooiUserGroupMapper.getUser(userId);
            if (observedReportCountMap.containsKey(user)) {
                int currentReportCount = observedReportCountMap.get(user);
                observedReportCountMap.put(user, currentReportCount + 1);
            } else {
                observedReportCountMap.put(user, 1);
            }
        }
        for (User user : users) {
            if (!observedReportCountMap.containsKey(user))
                observedReportCountMap.put(user, 0);
        }
        return observedReportCountMap;
    }

    public static Map<User, Integer> generateObservedReportValueCountMap(List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Map<User, Set<Value>> observedReportValueMap = new HashMap<>();
        for (ObservedReport observedReport : observedReports) {
            int userId = observedReport.getReportData().getUserId();
            User user = ooiUserGroupMapper.getUser(userId);
            Value value = observedReport.getValue();
            if (observedReportValueMap.containsKey(user)) {
                observedReportValueMap.get(user).add(value);
            } else {
                Set<Value> values = new HashSet<>();
                values.add(value);
                observedReportValueMap.put(user, values);
            }
        }
        Map<User, Integer> reportedValueCountMap = new HashMap<>();
        for (User user : observedReportValueMap.keySet()) {
            reportedValueCountMap.put(user, observedReportValueMap.get(user).size());
        }
        return reportedValueCountMap;
    }

    public static Map<PssGroup, Integer> generateReportCountByGroupMap(Set<PssGroup> pssGroups, List<ObservedReport> observedReports, OoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Map<PssGroup, Integer> reportCountMap = new HashMap<>();
        for (ObservedReport observedReport : observedReports) {
            int userId = observedReport.getReportData().getUserId();
            PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(userId);
            if (reportCountMap.containsKey(pssGroup)) {
                reportCountMap.put(pssGroup, reportCountMap.get(pssGroup) + 1);
            } else {
                reportCountMap.put(pssGroup, 1);
            }
        }
        return reportCountMap;
    }
}
