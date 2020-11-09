package pss.report.generation;

import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.data.user.User;
import pss.data.valuemap.MultiValueMap;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.mapping.ooi_user_group.MultiOoiUserGroupMappable;
import pss.report.observed.MultiObservedReport;
import pss.report.observed.MultiObservedReportData;

import java.util.Set;

public class RandomMultiObservedReportGenerator implements ObservedReportGenerable<MultiValueMap, MultiObservedReport, MultiOoiUserGroupMappable> {
    private final MyRandom random;

    public RandomMultiObservedReportGenerator(MyRandom random) {
        this.random = random;
    }

    @Override
    public MultiObservedReport generateObservedReport(int reportId, MultiValueMap valueMap, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        MultiOoiCombination multiOoiCombination = random.nextItem(valueMap.getValues().keySet());
        MultiPssGroup pssGroup = ooiUserGroupMapper.getPssGroup(multiOoiCombination);
        Set<User> users = ooiUserGroupMapper.getUsers(pssGroup);
        User user = random.nextItem(users);
        MultiLocalOoiCombination localOoiCombination = ooiUserGroupMapper.getLocalOoiCombination(multiOoiCombination);
        MultiObservedReportData observedReportData = new MultiObservedReportData(user.getId(), localOoiCombination);
        return new MultiObservedReport(reportId, valueMap.getValue(multiOoiCombination), observedReportData);
    }
}
