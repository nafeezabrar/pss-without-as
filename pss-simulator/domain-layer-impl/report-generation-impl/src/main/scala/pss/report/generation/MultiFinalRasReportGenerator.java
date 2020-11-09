package pss.report.generation;

import pss.data.anonymity.MultiRasAnonymity;
import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.pssvariable.PssVariables;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.data.valuemap.Value;
import pss.data.valuemap.ValueMap;
import pss.exception.PssException;
import pss.library.MyRandom;
import pss.local.ooi.anonymized.MultiAnonymizedLocalOoiSet;
import pss.mapping.ooi_user_group.OoiUserGroupMappable;
import pss.report.finalreport.FinalReport;
import pss.report.finalreport.MultiFinalReport;
import pss.report.finalreport.MultiFinalReportData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiFinalRasReportGenerator implements FinalReportInheritedGenerable<MultiLocalOoiCombination, MultiAnonymizedLocalOoiSet, MultiRasAnonymity, MultiLocalOoiCollection> {

    protected final MyRandom myRandom;
    protected final PssVariables pssVariables;
    protected final OoiUserGroupMappable ooiUserGroupMapper;

    public MultiFinalRasReportGenerator(MyRandom myRandom, PssVariables pssVariables, OoiUserGroupMappable ooiUserGroupMapper) {
        this.myRandom = myRandom;
        this.pssVariables = pssVariables;
        this.ooiUserGroupMapper = ooiUserGroupMapper;
    }

    @Override
    public FinalReport generateFinalReport(int reportId, Set<MultiLocalOoiCollection> multiLocalOoiCollections, MultiLocalOoiCombination reportedLocalOoiCombination, MultiAnonymizedLocalOoiSet anonymizedLocalOoiSet, User user, Value value, MultiRasAnonymity anonymity) throws PssException {
        Set<Dimension> dimensions = reportedLocalOoiCombination.getLocalOoiMap().keySet();
        Map<Dimension, Set<Integer>> anonymizedIdSet = new HashMap<>();
        Map<Dimension, Set<Integer>> anonymizedOoiSets = anonymizedLocalOoiSet.getAnonymizedOoiSets();
        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(user.getId());
        MultiLocalOoiMapper localOoiMapper = (MultiLocalOoiMapper) pssGroup.getLocalOoiMapper();
        MultiOoiCombination reportedOoiCombination = getOoiCombination(value);
        for (Dimension dimension : dimensions) {
            Set<Integer> anonymizedOois = getAnonymizedOois(dimension, value, reportedOoiCombination, anonymizedOoiSets, localOoiMapper.getOoiToIdMaps().get(dimension), anonymity, user);
            anonymizedIdSet.put(dimension, anonymizedOois);
        }
        MultiLocalOoiCollection multiLocalOoiCollection = new MultiLocalOoiCollection(anonymizedIdSet);
        MultiFinalReportData finalReportData = new MultiFinalReportData(user.getId(), multiLocalOoiCollection);
        return new MultiFinalReport(reportId, value, finalReportData);
    }

    private MultiOoiCombination getOoiCombination(Value value) {
        ValueMap valueMap = pssVariables.getValueMap();
        Map<MultiOoiCombination, Value> values = valueMap.getValues();
        for (MultiOoiCombination ooiCombination : values.keySet()) {
            if (values.get(ooiCombination) == value)
                return ooiCombination;
        }
        return null;
    }

    private Set<Integer> getAnonymizedOois(Dimension dimension, Value value, MultiOoiCombination reportedOoiCombination, Map<Dimension, Set<Integer>> anonymizedLocalOoiSet, Map<Ooi, Integer> ooiToIdMaps, MultiRasAnonymity anonymity, User user) throws PssException {
        Set<Integer> suggestedLocalOois = anonymizedLocalOoiSet.get(dimension);
        Set<Integer> finalAnonymizedOoiIdSet = new HashSet<>();

        PssGroup pssGroup = ooiUserGroupMapper.getPssGroup(user.getId());
        MultiLocalOoiMapper localOoiMapper = (MultiLocalOoiMapper) pssGroup.getLocalOoiMapper();
        Map<Ooi, Integer> ooiToIdMap = localOoiMapper.getOoiToIdMaps().get(dimension);
        Integer realLocalOoi = ooiToIdMap.get(reportedOoiCombination.getOoiMap().get(dimension));


        Set<Integer> candidateLocalOoisToReplace = new HashSet<>();
        candidateLocalOoisToReplace.addAll(ooiToIdMap.values());
        candidateLocalOoisToReplace.removeAll(suggestedLocalOois);


        int ooiToReplace = anonymity.getReplacingAnonymityMap().get(dimension);
        int ooiToKeep = anonymity.getAnonymityMap().get(dimension) - ooiToReplace;

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
        return finalAnonymizedOoiIdSet;
    }
}
