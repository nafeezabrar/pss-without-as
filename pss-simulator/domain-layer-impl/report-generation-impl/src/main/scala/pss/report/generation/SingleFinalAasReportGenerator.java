package pss.report.generation;

import pss.data.anonymity.SingleAasAnonymity;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.data.valuemap.SingleValueMap;
import pss.data.valuemap.Value;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.local.ooi.anonymized.SingleAnonymizedLocalOoiSet;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.report.finalreport.SingleFinalReport;
import pss.report.finalreport.SingleFinalReportData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SingleFinalAasReportGenerator implements FinalReportInheritedGenerable<SingleLocalOoiCombination, SingleAnonymizedLocalOoiSet, SingleAasAnonymity, SingleLocalOoiCollection> {
    protected final MyRandom myRandom;
    protected final PssVariables pssVariables;
    protected final OoiUserGroupMappable ooiUserGroupMapper;

    public SingleFinalAasReportGenerator(MyRandom myRandom, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper) {
        this.myRandom = myRandom;
        this.pssVariables = pssVariables;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
    }

    private SingleOoiCombination getReportedOoiId(Value value, SingleValueMap valueMap) {
        Map<SingleOoiCombination, Value> values = valueMap.getValues();
        for (SingleOoiCombination ooiCombination : values.keySet()) {
            if (values.get(ooiCombination).getIntValue() == value.getIntValue())
                return ooiCombination;
        }
        throw new UnsupportedOperationException("Value not found ");
    }

    @Override
    public FinalReport generateFinalReport(int reportId, Set<SingleLocalOoiCollection> singleLocalOoiCollections, SingleLocalOoiCombination reportedOoiCombination, SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet, User user, Value value, SingleAasAnonymity anonymity) throws PssException {
        SingleLocalOoiCollection suggestedOoiCollection = new SingleLocalOoiCollection(anonymizedLocalOoiSet.getAnonymizedIdSet());
        Set<Integer> finalAnonymizedOoiIdSet = new HashSet<>();
        finalAnonymizedOoiIdSet.addAll(suggestedOoiCollection.getLocalOoiSet());

        SingleOoiCombination ooiCombination = getReportedOoiId(value, (SingleValueMap) pssVariables.getValueMap());

        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(user.getId());
        SingleLocalOoiMapper localOoiMapper = (SingleLocalOoiMapper) pssGroup.getLocalOoiMapper();
        Map<Ooi, Integer> ooiToIdMap = localOoiMapper.getOoiToIdMap();
        Integer realOoiId = ooiToIdMap.get(ooiCombination.getOoi());

        finalAnonymizedOoiIdSet.add(realOoiId);
        int totalAdded = finalAnonymizedOoiIdSet.size();
        Collection<Integer> localOoiIds = ooiToIdMap.values();
        while (totalAdded != anonymity.getAnonymity()) {
            Integer localOoiId = myRandom.nextItem(localOoiIds);
            if (!finalAnonymizedOoiIdSet.contains(localOoiId)) {
                finalAnonymizedOoiIdSet.add(localOoiId);
                totalAdded++;
            }

        }
        SingleLocalOoiCollection anonymizedCollection = new SingleLocalOoiCollection(finalAnonymizedOoiIdSet);
        SingleFinalReportData reportData = new SingleFinalReportData(user.getId(), anonymizedCollection);
        return new SingleFinalReport(reportId, value, reportData);
    }
}
