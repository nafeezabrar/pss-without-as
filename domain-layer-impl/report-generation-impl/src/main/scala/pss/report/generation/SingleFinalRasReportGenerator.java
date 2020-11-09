package pss.report.generation;

import pss.data.anonymity.SingleRasAnonymity;
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SingleFinalRasReportGenerator implements FinalReportInheritedGenerable<SingleLocalOoiCombination, SingleAnonymizedLocalOoiSet, SingleRasAnonymity, SingleLocalOoiCollection> {
    protected final MyRandom myRandom;
    protected final PssVariables pssVariables;
    protected final OoiUserGroupMappable ooiUserGroupMapper;

    public SingleFinalRasReportGenerator(MyRandom myRandom, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper) {
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
    public FinalReport generateFinalReport(int reportId, Set<SingleLocalOoiCollection> singleLocalOoiCollections, SingleLocalOoiCombination reportedOoiCombination, SingleAnonymizedLocalOoiSet anonymizedLocalOoiSet, User user, Value value, SingleRasAnonymity anonymity) throws PssException {
        Set<Integer> suggestedLocalOois = anonymizedLocalOoiSet.getAnonymizedIdSet();
        Set<Integer> finalAnonymizedOoiIdSet = new HashSet<>();

        SingleOoiCombination ooiCombination = getReportedOoiId(value, (SingleValueMap) pssVariables.getValueMap());

        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(user.getId());
        SingleLocalOoiMapper localOoiMapper = (SingleLocalOoiMapper) pssGroup.getLocalOoiMapper();
        Map<Ooi, Integer> ooiToIdMap = localOoiMapper.getOoiToIdMap();
        Integer realLocalOoi = ooiToIdMap.get(ooiCombination.getOoi());


        Set<Integer> candidateLocalOoisToReplace = new HashSet<>();
        candidateLocalOoisToReplace.addAll(ooiToIdMap.values());
        candidateLocalOoisToReplace.removeAll(suggestedLocalOois);


        int ooiToReplace = anonymity.getReplacingAnonymity();
        int ooiToKeep = anonymity.getAnonymity() - ooiToReplace;

        finalAnonymizedOoiIdSet.add(realLocalOoi);
        if (suggestedLocalOois.contains(realLocalOoi)) {
            suggestedLocalOois.remove(realLocalOoi);
            ooiToKeep--;
        } else {
            candidateLocalOoisToReplace.remove(realLocalOoi);
            ooiToReplace--;
        }

        finalAnonymizedOoiIdSet.addAll(myRandom.nextItems(ooiToKeep, suggestedLocalOois));
        finalAnonymizedOoiIdSet.addAll(myRandom.nextItems(ooiToReplace, candidateLocalOoisToReplace));


        SingleLocalOoiCollection anonymizedCollection = new SingleLocalOoiCollection(finalAnonymizedOoiIdSet);
        SingleFinalReportData reportData = new SingleFinalReportData(user.getId(), anonymizedCollection);
        return new SingleFinalReport(reportId, value, reportData);
    }
}
