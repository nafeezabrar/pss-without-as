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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RandomMultiObservedReportSetGenerator implements ObservedReportSetGenerable<MultiValueMap, MultiObservedReport, MultiOoiUserGroupMappable> {

    protected final int reportCount;
    private final MyRandom random;

    public RandomMultiObservedReportSetGenerator(MyRandom random, int reportCount) {
        this.random = random;
        this.reportCount = reportCount;
    }


    @Override
    public List<MultiObservedReport> generateObservedReports(MultiValueMap valueMap, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        List<MultiObservedReport> observedReports = new ArrayList<>();
        for (int i = 0; i < reportCount; i++) {
            observedReports.add(generateObservedReport(i + 1, valueMap, ooiUserGroupMapper));
        }
        return observedReports;
    }

    private MultiObservedReport generateObservedReport(int reportId, MultiValueMap valueMap, MultiOoiUserGroupMappable ooiUserGroupMapper) throws PssException {
        MultiOoiCombination multiOoiCombination = random.nextItem(valueMap.getValues().keySet());
        MultiPssGroup pssGroup = ooiUserGroupMapper.getPssGroup(multiOoiCombination);
        Set<User> users = ooiUserGroupMapper.getUsers(pssGroup);
        User user = random.nextItem(users);
        MultiLocalOoiCombination localOoiCombination = ooiUserGroupMapper.getLocalOoiCombination(multiOoiCombination);
        MultiObservedReportData observedReportData = new MultiObservedReportData(user.getId(), localOoiCombination);
        return new MultiObservedReport(reportId, valueMap.getValue(multiOoiCombination), observedReportData);
    }
}
