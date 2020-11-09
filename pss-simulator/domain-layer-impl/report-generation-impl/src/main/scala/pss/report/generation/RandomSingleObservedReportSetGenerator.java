package pss.report.generation;


import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pssvariable.group.SinglePssGroup;
import pss.data.user.User;
import pss.data.valuemap.SingleValueMap;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.mapping.ooi_user_group.SingleOoiUserGroupMappable;
import pss.report.observed.SingleObservedReport;
import pss.report.observed.SingleObservedReportData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RandomSingleObservedReportSetGenerator implements ObservedReportSetGenerable<SingleValueMap, SingleObservedReport, SingleOoiUserGroupMappable> {

    protected final MyRandom myRandom;
    protected final int reportCount;

    public RandomSingleObservedReportSetGenerator(MyRandom myRandom, int reportCount) {
        this.myRandom = myRandom;
        this.reportCount = reportCount;
    }

    @Override
    public List<SingleObservedReport> generateObservedReports(SingleValueMap singleValueMap, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        List<SingleObservedReport> observedReports = new ArrayList<>();
        for (int i = 1; i <= reportCount; i++) {
            SingleObservedReport singleObservedReport = generateObservedReport(i, singleValueMap, ooiUserGroupMapper);
            observedReports.add(singleObservedReport);
        }
        return observedReports;
    }

    private SingleObservedReport generateObservedReport(int reportId, SingleValueMap singleValueMap, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Set<SingleOoiCombination> ooiCombinations = singleValueMap.getValues().keySet();
        SingleOoiCombination singleOoiCombination = myRandom.nextItem(ooiCombinations);
//        SingleOoiCombination singleOoiCombination = getFixedOoiCombination(ooiCombinations);
        SinglePssGroup pssGroup = ooiUserGroupMapper.getPssGroup(singleOoiCombination);
        Set<User> users = ooiUserGroupMapper.getUsers(pssGroup);
        User user = myRandom.nextItem(users);
        SingleLocalOoiCombination localOoiCombination = ooiUserGroupMapper.getLocalOoiCombination(singleOoiCombination);
        SingleObservedReportData observedReportData = new SingleObservedReportData(user.getId(), localOoiCombination);
        return new SingleObservedReport(reportId, singleValueMap.getValue(singleOoiCombination), observedReportData);
    }

    private SingleOoiCombination getFixedOoiCombination(Set<SingleOoiCombination> ooiCombinations) throws PssException {
        for (SingleOoiCombination ooiCombination : ooiCombinations) {
            if (ooiCombination.getOoi().getId() == 1)
                return ooiCombination;
        }
        throw new PssException("not found ooi with id one");
    }
}
