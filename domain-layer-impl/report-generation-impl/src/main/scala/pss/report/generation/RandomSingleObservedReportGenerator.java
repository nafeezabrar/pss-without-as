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

import java.util.Set;

public class RandomSingleObservedReportGenerator implements ObservedReportGenerable<SingleValueMap, SingleObservedReport, SingleOoiUserGroupMappable> {

    protected final MyRandom myRandom;

    public RandomSingleObservedReportGenerator(MyRandom myRandom) {
        this.myRandom = myRandom;
    }

    @Override
    public SingleObservedReport generateObservedReport(int reportId, SingleValueMap singleValueMap, SingleOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        Set<SingleOoiCombination> ooiCombinations = singleValueMap.getValues().keySet();
        SingleOoiCombination singleOoiCombination = myRandom.nextItem(ooiCombinations);
        SinglePssGroup pssGroup = ooiUserGroupMapper.getPssGroup(singleOoiCombination);
        Set<User> users = ooiUserGroupMapper.getUsers(pssGroup);
        User user = myRandom.nextItem(users);
        SingleLocalOoiCombination localOoiCombination = ooiUserGroupMapper.getLocalOoiCombination(singleOoiCombination);
        SingleObservedReportData observedReportData = new SingleObservedReportData(user.getId(), localOoiCombination);
        return new SingleObservedReport(reportId, singleValueMap.getValue(singleOoiCombination), observedReportData);
    }
}
